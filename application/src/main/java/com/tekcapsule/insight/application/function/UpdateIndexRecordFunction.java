package com.tekcapsule.insight.application.function;

import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.PayloadUtil;
import com.tekcapsule.core.utils.Stage;
import com.tekcapsule.insight.application.function.input.UpdateIndexRecordInput;
import com.tekcapsule.insight.domain.command.UpdateIndexRecordCommand;
import com.tekcapsule.insight.application.config.AppConfig;
import com.tekcapsule.insight.application.mapper.InputOutputMapper;
import com.tekcapsule.insight.domain.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class UpdateIndexRecordFunction implements Function<Message<UpdateIndexRecordInput>, Message<Void>> {

    private final IndexService indexService;

    private final AppConfig appConfig;

    public UpdateIndexRecordFunction(final IndexService indexService, final AppConfig appConfig) {
        this.indexService = indexService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<UpdateIndexRecordInput> updateInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();

        try {
            UpdateIndexRecordInput updateInput = updateInputMessage.getPayload();
            log.info(String.format("Entering update index Function - Insight Id:%s", updateInput.getInsightId()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(updateInputMessage.getHeaders());
            UpdateIndexRecordCommand updateIndexRecordCommand = InputOutputMapper.buildUpdateIndexRecordCommandFromUpdateIndexRecordInput.apply(updateInput, origin);
            indexService.update(updateIndexRecordCommand);
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