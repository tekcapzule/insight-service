package com.tekcapzule.insight.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.tekcapzule.insight.domain.exception.InsightServiceException;
import com.tekcapzule.insight.domain.model.Symbol;
import com.tekcapzule.insight.domain.model.Tickers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class YamlParserServiceImpl implements YamlParserService{

    private static final String TICKER_FILE_NAME = "ticker.yml";
    @Override
    public Map<String, List<Symbol>> getStockIndexMap() throws InsightServiceException{

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.findAndRegisterModules();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(TICKER_FILE_NAME);
        Tickers tickers;
        try {
            tickers = objectMapper.readValue(inputStream, Tickers.class);
            log.info("tickers ::: "+tickers.getTickers());
        } catch (IOException e) {
            log.error("Error parsing ticker file");
            throw new InsightServiceException("Error parsing the ticker file", e);
        }
        if(inputStream == null){
            log.error("Unable to find the ticker File");
            throw new InsightServiceException("Unable to find the ticker File", new FileNotFoundException());
        }
        return tickers.getTickers();
    }

}
