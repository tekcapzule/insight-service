package com.tekcapzule.insight.domain.service;

import com.tekcapzule.insight.domain.exception.InsightServiceException;

public interface TickerService {
    void createOrUpdateTickerDetails() throws InsightServiceException;
}
