package com.tekcapsule.insight.application.function;

import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.Stage;
import com.tekcapsule.insight.application.config.AppConfig;
import com.tekcapsule.insight.application.function.input.GetIndexRecordsInput;
import com.tekcapsule.insight.domain.model.IndexRecord;
import com.tekcapsule.insight.domain.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component
@Slf4j
public class GetAllIndexRecordsFunction implements Function<Message<GetIndexRecordsInput>, Message<List<IndexRecord>>> {

    private final IndexService indexService;

    private final AppConfig appConfig;

    public GetAllIndexRecordsFunction(final IndexService indexService, final AppConfig appConfig) {
        this.indexService = indexService;
        this.appConfig = appConfig;
    }


    @Override
    public Message<List<IndexRecord>> apply(Message<GetIndexRecordsInput> getAllInputMessage) {
        GetIndexRecordsInput getIndexRecordsInput = getAllInputMessage.getPayload();
        Map<String, Object> responseHeaders = new HashMap<>();
        List<IndexRecord> indexRecords = new ArrayList<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            log.info("Entering get all index Function");
            indexRecords = indexService.findAll(getIndexRecordsInput.getStartsFrom());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage<>(indexRecords, responseHeaders);
    }
}