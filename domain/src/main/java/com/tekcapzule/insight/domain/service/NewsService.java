package com.tekcapzule.insight.domain.service;

import com.tekcapzule.insight.domain.command.CreateNewsCommand;
import com.tekcapzule.insight.domain.command.UpdateNewsCommand;
import com.tekcapzule.insight.domain.model.News;

import java.util.List;

public interface NewsService {

    void create(CreateNewsCommand createNewsCommand);
    void update(UpdateNewsCommand updateNewsCommand);
    List<News> findAll(String startsFrom, String topic);
}
