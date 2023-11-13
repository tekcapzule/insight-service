package com.tekcapzule.insight.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Topic {
    AI("AI"),
    METAVERSE("Metaverse"),
    WEB3("Web 3.0");

    @Getter
    private String value;
}