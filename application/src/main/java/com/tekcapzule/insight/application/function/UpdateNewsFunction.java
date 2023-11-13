package com.tekcapzule.insight.application.function;

import com.tekcapzule.core.domain.Origin;
import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.PayloadUtil;
import com.tekcapzule.core.utils.Stage;
import com.tekcapzule.insight.application.function.input.UpdateNewsInput;
import com.tekcapzule.insight.application.mapper.InputOutputMapper;
import com.tekcapzule.insight.application.config.AppConfig;
import com.tekcapzule.insight.domain.command.UpdateNewsCommand;
import com.tekcapzule.insight.domain.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class UpdateNewsFunction implements Function<Message<UpdateNewsInput>, Message<Void>> {

    private final NewsService newsService;

    private final AppConfig appConfig;

    public UpdateNewsFunction(final NewsService newsService, final AppConfig appConfig) {
        this.newsService = newsService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<UpdateNewsInput> updateInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();

        try {
            UpdateNewsInput updateInput = updateInputMessage.getPayload();
            log.info(String.format("Entering update news Function - Insight Id:%s", updateInput.getInsightId()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(updateInputMessage.getHeaders());
            UpdateNewsCommand updateNewsCommand = InputOutputMapper.buildUpdateNewsCommandFromUpdateNewsInput.apply(updateInput, origin);
            newsService.update(updateNewsCommand);
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