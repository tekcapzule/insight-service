package com.tekcapzule.insight.domain.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Tickers {
    private Map<String, List<Symbol>> tickers;
}
