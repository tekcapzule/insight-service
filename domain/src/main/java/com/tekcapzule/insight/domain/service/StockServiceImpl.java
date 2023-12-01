package com.tekcapzule.insight.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tekcapzule.insight.domain.exception.InsightServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Slf4j
@Service
public class StockServiceImpl implements StockService{
    private Environment environment;
    public StockServiceImpl(Environment environment){
        this.environment = environment;
    }
    @Override
    public Map<String, Double> getStockQuotes(String symbol) throws InsightServiceException{
        log.info(String.format("Inside getStockQuotes for : %s", symbol));
        String apiUrl = environment.getProperty("REAL_STONKS_STOCK_SERVICE_API_URL");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(apiUrl, symbol)))
                .header("X-RapidAPI-Key", environment.getProperty("REAL_STONKS_STOCK_SERVICE_API_KEY"))
                .header("X-RapidAPI-Host", environment.getProperty("REAL_STONKS_STOCK_SERVICE_API_HOST"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = null;
        Map<String, Double> stockQuotes;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            stockQuotes = (Map) mapper.createParser(response.body()).readValueAs(Map.class);
        } catch (IOException e) {
            throw new InsightServiceException("Error getting response from Real Stonks Vantage service", e);
        } catch (InterruptedException e) {
            throw new InsightServiceException("Error Connecting to Real Stonks service", e);
        }
        return stockQuotes;
    }
}
