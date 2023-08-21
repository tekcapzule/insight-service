package com.tekcapsule.insight.application.function.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.insight.domain.model.Stock;
import com.tekcapsule.insight.domain.model.StockIndex;
import com.tekcapsule.insight.domain.model.Topic;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class CreateIndexRecordInput {
    private StockIndex stockIndex;
    private Topic topic;
    private String closingOn;
    private BigDecimal valueOnClosing;
    private List<Stock> stocks;
    private String comment;
}