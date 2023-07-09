package com.tekcapsule.insight.domain.repository;

import com.tekcapsule.core.domain.CrudRepository;
import com.tekcapsule.insight.domain.model.Course;

import java.util.List;

public interface InsightRepository extends CrudRepository<Course, String> {

    List<Course> findAllByTopicCode(String topicCode);
}
