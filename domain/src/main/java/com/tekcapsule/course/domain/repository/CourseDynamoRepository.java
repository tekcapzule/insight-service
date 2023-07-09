package com.tekcapsule.course.domain.repository;

import com.tekcapsule.core.domain.CrudRepository;
import com.tekcapsule.course.domain.model.Course;

import java.util.List;

public interface CourseDynamoRepository extends CrudRepository<Course, String> {

    List<Course> findAllByTopicCode(String topicCode);
}
