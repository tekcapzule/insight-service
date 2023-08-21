package com.tekcapsule.insight.domain.service;

import com.sun.tools.javac.util.List;
import com.tekcapsule.insight.domain.command.CreateNewsCommand;
import com.tekcapsule.insight.domain.command.UpdateNewsCommand;
import com.tekcapsule.insight.domain.model.News;
import com.tekcapsule.insight.domain.model.Status;
import com.tekcapsule.insight.domain.repository.NewsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NewsServiceImpl implements NewsService {
    private NewsRepository newsRepository;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public void create(CreateNewsCommand createNewsCommand) {

        log.info(String.format("Entering create news service - News Title :%s", createNewsCommand.getTitle()));

        News news = News.builder()
                .title(createNewsCommand.getTitle())
                .description(createNewsCommand.getDescription())
                .topic(createNewsCommand.getTopic())
                .tags(createNewsCommand.getTags())
                .publishedOn(createNewsCommand.getPublishedOn())
                .status(Status.ACTIVE)
                .build();

        news.setAddedOn(createNewsCommand.getExecOn());
        news.setAddedBy(createNewsCommand.getExecBy().getUserId());
        newsRepository.save(news);
    }

    @Override
    public void update(UpdateNewsCommand updateNewsCommand) {

        log.info(String.format("Entering update news service - Insight ID:%s", updateNewsCommand.getInsightId()));

        News news = newsRepository.findBy(updateNewsCommand.getInsightId());
        if (news != null) {
            news.setTitle(updateNewsCommand.getTitle());
            news.setDescription(updateNewsCommand.getDescription());
            news.setTags(updateNewsCommand.getTags());
            news.setTopic(updateNewsCommand.getTopic());
            news.setPublishedOn(updateNewsCommand.getPublishedOn());
            news.setUpdatedOn(updateNewsCommand.getExecOn());
            news.setUpdatedBy(updateNewsCommand.getExecBy().getUserId());
            newsRepository.save(news);
        }
    }
    @Override
    public List<News> findAll() {

        log.info("Entering findAll news service");

        return newsRepository.findAll();
    }

}
