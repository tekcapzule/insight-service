package com.tekcapsule.insight.domain.service;

import com.tekcapsule.insight.domain.command.CreateNewsCommand;
import com.tekcapsule.insight.domain.command.UpdateNewsCommand;
import com.tekcapsule.insight.domain.model.News;

import java.util.List;

public interface NewsService {

    void create(CreateNewsCommand createNewsCommand);
    void update(UpdateNewsCommand updateNewsCommand);
    List<News> findAll(String startsFrom);
}
