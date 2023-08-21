package com.tekcapsule.insights.application.function;

import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.PayloadUtil;
import com.tekcapsule.core.utils.Stage;
import com.tekcapsule.insight.domain.command.CreateCommand;
import com.tekcapsule.insight.domain.service.InsightService;
import com.tekcapsule.insights.application.config.AppConfig;
import com.tekcapsule.insights.application.function.input.CreateNewsInput;
import com.tekcapsule.insights.application.mapper.InputOutputMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class CreateStocksFunction implements Function<Message<CreateNewsInput>, Message<Void>> {

    private final InsightService insightService;

    private final AppConfig appConfig;

    public CreateStocksFunction(final InsightService insightService, final AppConfig appConfig) {
        this.insightService = insightService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<CreateNewsInput> createInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();

        try {
            CreateNewsInput createInput = createInputMessage.getPayload();
            log.info(String.format("Entering create course Function - Module Code:%s", createInput.getTopicCode()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(createInputMessage.getHeaders());
            CreateCommand createCommand = InputOutputMapper.buildCreateCommandFromCreateInput.apply(createInput, origin);
            insightService.create(createCommand);
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