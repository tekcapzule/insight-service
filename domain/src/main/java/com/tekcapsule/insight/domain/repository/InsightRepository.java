package com.tekcapsule.insight.domain.repository;

import com.tekcapsule.core.domain.CrudRepository;
import com.tekcapsule.insight.domain.model.Insights;

import java.util.List;

public interface InsightRepository extends CrudRepository<Insights, String> {

    List<Insights> findAllByTopicCode(String topicCode);
}
