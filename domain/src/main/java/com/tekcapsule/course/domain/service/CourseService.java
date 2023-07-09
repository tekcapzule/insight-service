package com.tekcapsule.course.domain.service;

import com.tekcapsule.course.domain.command.CreateCommand;
import com.tekcapsule.course.domain.command.UpdateCommand;
import com.tekcapsule.course.domain.model.Course;
import java.util.List;


public interface CourseService {

    void create(CreateCommand createCommand);

    void update(UpdateCommand updateCommand);

    List<Course> findAll();

    List<Course> findAllByTopicCode(String code);
}
