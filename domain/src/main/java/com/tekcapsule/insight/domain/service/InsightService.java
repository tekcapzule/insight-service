package com.tekcapsule.insight.domain.service;

import com.tekcapsule.insight.domain.command.CreateCommand;
import com.tekcapsule.insight.domain.command.UpdateCommand;
import com.tekcapsule.insight.domain.model.Course;
import java.util.List;


public interface InsightService {

    void create(CreateCommand createCommand);

    void update(UpdateCommand updateCommand);

    List<Course> findAll();

    List<Course> findAllByTopicCode(String code);
}
