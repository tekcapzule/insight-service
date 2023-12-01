package com.tekcapzule.insight.domain.model;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class Symbol {
    private String symbol;
    private String name;
    private BigDecimal weightage;
}
