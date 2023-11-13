package com.tekcapzule.insight.application.function;

import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.Stage;
import com.tekcapzule.insight.application.config.AppConfig;
import com.tekcapzule.insight.application.function.input.GetIndexRecordsInput;
import com.tekcapzule.insight.domain.model.IndexRecord;
import com.tekcapzule.insight.domain.service.IndexService;
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
            indexRecords = indexService.findAll(getIndexRecordsInput.getStartsFrom(), getIndexRecordsInput.getTopic());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage<>(indexRecords, responseHeaders);
    }
}