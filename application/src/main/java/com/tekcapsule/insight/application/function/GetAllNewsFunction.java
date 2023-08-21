package com.tekcapsule.insight.application.function;

import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.Stage;
import com.tekcapsule.insight.application.config.AppConfig;
import com.tekcapsule.insight.application.function.input.GetNewsInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class GetAllNewsFunction implements Function<Message<GetNewsInput>, Message<List<Insights>>> {

    private final InsightService insightService;

    private final AppConfig appConfig;

    public GetAllNewsFunction(final InsightService insightService, final AppConfig appConfig) {
        this.insightService = insightService;
        this.appConfig = appConfig;
    }


    @Override
    public Message<List<Insights>> apply(Message<GetNewsInput> getInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        List<Insights> cours = new ArrayList<>();

        String stage = appConfig.getStage().toUpperCase();

        try {
            GetNewsInput getInput = getInputMessage.getPayload();
            log.info(String.format("Entering get course Function -Module Code:%s", getInput.getInsightsId()));
            cours = insightService.findAllByTopicCode(getInput.getInsightsId());
            if (cours.isEmpty()) {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.NOT_FOUND);
            } else {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage<>(cours, responseHeaders);
    }
}