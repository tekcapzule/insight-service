package com.tekcapzule.insight.domain.service;

import com.tekcapzule.insight.domain.exception.InsightServiceException;
import com.tekcapzule.insight.domain.model.Symbol;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public interface YamlParserService {
    public Map<String, List<Symbol>> getStockIndexMap() throws InsightServiceException;
}
