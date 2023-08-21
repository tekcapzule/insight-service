package com.tekcapsule.insight.domain.repository;

import com.tekcapsule.core.domain.CrudRepository;
import com.tekcapsule.insight.domain.model.News;

import java.util.List;

public interface NewsRepository extends CrudRepository<News, String> {
    List<News> findAll (String startsFrom);

}
