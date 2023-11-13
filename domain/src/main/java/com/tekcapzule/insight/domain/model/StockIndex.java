package com.tekcapzule.insight.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StockIndex {
    META("Meta"),
    WEB3("Web 3"),
    AI("AI");

    @Getter
    private String value;
}