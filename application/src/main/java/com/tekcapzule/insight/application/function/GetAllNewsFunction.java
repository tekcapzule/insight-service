package com.tekcapzule.insight.application.function;

import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.Stage;
import com.tekcapzule.insight.application.config.AppConfig;
import com.tekcapzule.insight.application.function.input.GetNewsInput;
import com.tekcapzule.insight.domain.model.News;
import com.tekcapzule.insight.domain.service.NewsService;
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
public class GetAllNewsFunction implements Function<Message<GetNewsInput>, Message<List<News>>> {

    private final NewsService newsService;

    private final AppConfig appConfig;

    public GetAllNewsFunction(final NewsService newsService, final AppConfig appConfig) {
        this.newsService = newsService;
        this.appConfig = appConfig;
    }


    @Override
    public Message<List<News>> apply(Message<GetNewsInput> getInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        List<News> news = new ArrayList<>();

        String stage = appConfig.getStage().toUpperCase();

        try {
            GetNewsInput getInput = getInputMessage.getPayload();
            log.info(String.format("Entering getall News Function -Start From:%s", getInput.getStartsFrom()));
            news = newsService.findAll(getInput.getStartsFrom(), getInput.getTopic());
            if (news.isEmpty()) {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.NOT_FOUND);
            } else {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage<>(news, responseHeaders);
    }
}