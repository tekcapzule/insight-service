package com.tekcapzule.insight.application.function;

import com.tekcapzule.core.domain.Origin;
import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.PayloadUtil;
import com.tekcapzule.core.utils.Stage;
import com.tekcapzule.insight.application.function.input.UpdateIndexRecordInput;
import com.tekcapzule.insight.domain.command.UpdateIndexRecordCommand;
import com.tekcapzule.insight.application.config.AppConfig;
import com.tekcapzule.insight.application.mapper.InputOutputMapper;
import com.tekcapzule.insight.domain.service.IndexService;
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