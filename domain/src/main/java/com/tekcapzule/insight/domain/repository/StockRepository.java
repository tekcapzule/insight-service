package com.tekcapzule.insight.domain.repository;

import com.tekcapzule.core.domain.CrudRepository;
import com.tekcapzule.insight.domain.model.IndexRecord;
import com.tekcapzule.insight.domain.model.Stock;

import java.util.List;

public interface StockRepository extends CrudRepository<Stock, String> {
    List<Stock> findAll (String startsFrom, String topic);
}
