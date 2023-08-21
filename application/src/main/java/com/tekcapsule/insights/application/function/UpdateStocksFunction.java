package com.tekcapsule.insights.application.function;

import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.PayloadUtil;
import com.tekcapsule.core.utils.Stage;
import com.tekcapsule.insight.domain.command.UpdateCommand;
import com.tekcapsule.insight.domain.service.InsightService;
import com.tekcapsule.insights.application.config.AppConfig;
import com.tekcapsule.insights.application.function.input.UpdateNewsInput;
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
public class UpdateStocksFunction implements Function<Message<UpdateNewsInput>, Message<Void>> {

    private final InsightService insightService;

    private final AppConfig appConfig;

    public UpdateStocksFunction(final InsightService insightService, final AppConfig appConfig) {
        this.insightService = insightService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<UpdateNewsInput> updateInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();

        try {
            UpdateNewsInput updateInput = updateInputMessage.getPayload();
            log.info(String.format("Entering update course Function - Module Code:%s", updateInput.getTopicCode()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(updateInputMessage.getHeaders());
            UpdateCommand updateCommand = InputOutputMapper.buildUpdateCommandFromUpdateInput.apply(updateInput, origin);
            insightService.update(updateCommand);
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