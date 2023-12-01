package com.tekcapzule.insight.application.function;

import com.tekcapzule.core.domain.Origin;
import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.PayloadUtil;
import com.tekcapzule.core.utils.Stage;
import com.tekcapzule.insight.application.function.input.CreateIndexRecordInput;
import com.tekcapzule.insight.domain.command.CreateIndexRecordCommand;
import com.tekcapzule.insight.application.config.AppConfig;
import com.tekcapzule.insight.application.mapper.InputOutputMapper;
import com.tekcapzule.insight.domain.service.IndexService;
import com.tekcapzule.insight.domain.service.TickerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class LoadTickerFunction implements Function<Message<Void>, Message<Void>> {

    private final TickerService tickerService;

    private final AppConfig appConfig;

    public LoadTickerFunction(final TickerService tickerService, final AppConfig appConfig) {
        this.tickerService = tickerService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<Void> createIndexRecordInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();

        try {
            log.info(String.format("Entering load ticker Function "));
            Origin origin = HeaderUtil.buildOriginFromHeaders(createIndexRecordInputMessage.getHeaders());
            tickerService.createOrUpdateTickerDetails();
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
            payload = PayloadUtil.composePayload(Outcome.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
            payload = PayloadUtil.composePayload(Outcome.ERROR);
        }
        return new GenericMessage(payload, responseHeaders);
    }
}