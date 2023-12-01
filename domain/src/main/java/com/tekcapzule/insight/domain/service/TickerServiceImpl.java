package com.tekcapzule.insight.domain.service;

import com.tekcapzule.insight.domain.exception.InsightServiceException;
import com.tekcapzule.insight.domain.model.*;
import com.tekcapzule.insight.domain.repository.IndexDynamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
public class TickerServiceImpl implements TickerService{

    private final YamlParserService yamlParserService;
    private final StockService stockService;
    private final IndexDynamoRepository indexDynamoRepository;

    public TickerServiceImpl(YamlParserService yamlParserService,
                             StockService stockService, IndexDynamoRepository indexDynamoRepository){
        this.yamlParserService = yamlParserService;
        this.stockService = stockService;
        this.indexDynamoRepository = indexDynamoRepository;
    }
    @Override
    public void createOrUpdateTickerDetails() throws InsightServiceException {
        log.info("In createOrUpdateTickerDetails");
        Map<String, List<Symbol>> tickers = yamlParserService.getStockIndexMap();
        log.info(String.format("After reading ticker.yml : %s", tickers.size()));
        for (Map.Entry<String, List<Symbol>> ticker : tickers.entrySet()) {
            IndexRecord indexRecord = new IndexRecord();
            indexRecord.setStockIndex(StockIndex.valueOf(ticker.getKey()));
            indexRecord.setTopic(Topic.valueOf(ticker.getKey()));
            List<Stock> stocks = new ArrayList<>();
            List<Symbol> symbols = ticker.getValue();
            BigDecimal indexValue = new BigDecimal(0.0);
            Map<String, Double> stockQuote = null;
            for (Symbol symbol: symbols) {
                stockQuote = stockService.getStockQuotes(symbol.getSymbol());
                BigDecimal stockPrice = BigDecimal.valueOf(stockQuote.get("price"));
                log.info(String.format("symbolWithPrice : %s :: %s :: %s", symbol.getSymbol(), symbol.getName(), stockPrice));
                Stock.StockBuilder stockBuilder = Stock.builder();
                stockBuilder.companyName(symbol.getName())
                        .valueOnClosing(stockPrice)
                        .symbol(symbol.getSymbol());
                stocks.add(stockBuilder.build());
                indexValue = indexValue.add(symbol.getWeightage().multiply(stockPrice));
            }
            indexRecord.setStocks(stocks);
            indexRecord.setValueOnClosing(indexValue);
            indexDynamoRepository.save(indexRecord);
        }

    }
}
