package com.tekcapzule.insight.domain.repository;

import com.tekcapzule.core.domain.CrudRepository;
import com.tekcapzule.insight.domain.model.IndexRecord;

import java.util.List;

public interface IndexRepository extends CrudRepository<IndexRecord, String> {
    List<IndexRecord> findAll (String startsFrom, String topic);
}
