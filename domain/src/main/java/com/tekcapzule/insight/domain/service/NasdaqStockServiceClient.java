package com.tekcapzule.insight.domain.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tekcapzule.insight.domain.model.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
@Slf4j
@Service
public class NasdaqStockServiceClient {
    private Environment environment;

    public NasdaqStockServiceClient(Environment environment){
        this.environment = environment;
    }

    public List<Stock> getStockQuotes(String commaSeparatedTickers1){
        log.info(String.format("In getStockQuotes :: finding Quotes for %s", commaSeparatedTickers1));
        String apiUrl = environment.getProperty("NASDAQ_STOCK_SERVICE_API_URL");
        commaSeparatedTickers1 = commaSeparatedTickers1.replace(",", "%2C");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(apiUrl, commaSeparatedTickers1)))
                .header("X-RapidAPI-Key", environment.getProperty("NASDAQ_STOCK_SERVICE_API_KEY"))
                .header("X-RapidAPI-Host", environment.getProperty("NASDAQ_STOCK_SERVICE_API_HOST"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        List<Stock> stockQuotes;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            log.info(response.body());
            ObjectMapper mapper = new ObjectMapper();
            stockQuotes = mapper.createParser(response.body()).readValueAs(new TypeReference<List<Stock>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return stockQuotes;
    }
}
