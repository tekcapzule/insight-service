package com.tekcapzule.insight.domain.service;

import com.tekcapzule.insight.domain.exception.InsightServiceException;

import java.util.Map;

public interface StockService {
    Map<String, Double> getStockQuotes(String symbol) throws InsightServiceException;
}
