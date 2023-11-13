package com.tekcapzule.insight.domain.service;
import com.tekcapzule.insight.domain.command.CreateIndexRecordCommand;
import com.tekcapzule.insight.domain.command.UpdateIndexRecordCommand;
import com.tekcapzule.insight.domain.model.IndexRecord;

import java.util.List;

public interface IndexService {

    void create(CreateIndexRecordCommand createIndexRecordCommand);
    void update(UpdateIndexRecordCommand updateIndexRecordCommand);
    List<IndexRecord> findAll(String startsFrom, String topic);
}
