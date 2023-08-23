package com.tekcapsule.insight.domain.service;
import com.tekcapsule.insight.domain.command.CreateIndexRecordCommand;
import com.tekcapsule.insight.domain.command.UpdateIndexRecordCommand;
import com.tekcapsule.insight.domain.model.IndexRecord;

import java.util.List;

public interface IndexService {

    void create(CreateIndexRecordCommand createIndexRecordCommand);
    void update(UpdateIndexRecordCommand updateIndexRecordCommand);
    List<IndexRecord> findAll(String startsFrom, String topic);
}
