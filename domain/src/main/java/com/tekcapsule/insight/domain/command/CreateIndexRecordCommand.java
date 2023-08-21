package com.tekcapsule.insight.domain.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.core.domain.Command;
import com.tekcapsule.insight.domain.model.Stock;
import com.tekcapsule.insight.domain.model.StockIndex;
import com.tekcapsule.insight.domain.model.Topic;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CreateIndexRecordCommand extends Command {
    private StockIndex stockIndex;
    private Topic topic;
    private String publishedOn;
    private BigDecimal valueOnClosing;
    private List<Stock> stocks;
    private String comment;
}

