package com.tekcapsule.insight.application.function;

import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.PayloadUtil;
import com.tekcapsule.core.utils.Stage;
import com.tekcapsule.insight.application.function.input.CreateIndexRecordInput;
import com.tekcapsule.insight.domain.command.CreateIndexRecordCommand;
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
public class CreateIndexRecordFunction implements Function<Message<CreateIndexRecordInput>, Message<Void>> {

    private final IndexService indexService;

    private final AppConfig appConfig;

    public CreateIndexRecordFunction(final IndexService indexService, final AppConfig appConfig) {
        this.indexService = indexService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<CreateIndexRecordInput> createIndexRecordInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();

        try {
            CreateIndexRecordInput createIndexRecordInput = createIndexRecordInputMessage.getPayload();
            log.info(String.format("Entering create index Function - Index Name:%s", createIndexRecordInput.getStockIndex().getValue()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(createIndexRecordInputMessage.getHeaders());
            CreateIndexRecordCommand createIndexRecordCommand = InputOutputMapper.buildCreateIndexRecordCommandFromCreateIndexRecordInput.apply(createIndexRecordInput, origin);
            indexService.create(createIndexRecordCommand);
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