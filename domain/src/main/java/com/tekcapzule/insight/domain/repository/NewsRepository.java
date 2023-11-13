package com.tekcapzule.insight.domain.repository;

import com.tekcapzule.core.domain.CrudRepository;
import com.tekcapzule.insight.domain.model.News;

import java.util.List;

public interface NewsRepository extends CrudRepository<News, String> {
    List<News> findAll (String startsFrom, String topic);

}
