package com.tekcapsule.insight.domain.service;
import com.sun.tools.javac.util.List;
import com.tekcapsule.insight.domain.command.CreateIndexRecordCommand;
import com.tekcapsule.insight.domain.command.UpdateIndexRecordCommand;
import com.tekcapsule.insight.domain.model.IndexRecord;

public interface IndexService {

    void create(CreateIndexRecordCommand createIndexRecordCommand);
    void update(UpdateIndexRecordCommand updateIndexRecordCommand);
    List<IndexRecord> findAll();
}
