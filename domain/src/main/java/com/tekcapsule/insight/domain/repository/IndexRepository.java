package com.tekcapsule.insight.domain.repository;

import com.tekcapsule.core.domain.CrudRepository;
import com.tekcapsule.insight.domain.model.IndexRecord;

import java.util.List;

public interface IndexRepository extends CrudRepository<IndexRecord, String> {
    List<IndexRecord> findAll (String startsFrom, String topic);
}
