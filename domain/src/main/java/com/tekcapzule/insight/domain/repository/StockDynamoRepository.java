package com.tekcapzule.insight.domain.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.tekcapzule.insight.domain.model.IndexRecord;
import com.tekcapzule.insight.domain.model.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository
public class StockDynamoRepository implements StockRepository {
    private DynamoDBMapper dynamo;
    public static final String ACTIVE_STATUS = "ACTIVE";

    @Autowired
    public StockDynamoRepository(DynamoDBMapper dynamo) {
        this.dynamo = dynamo;
    }

    @Override
    public List<Stock> findAll(String startsFrom, String topic) {
        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":startsFrom", new AttributeValue().withS(startsFrom));
        expAttributes.put(":topic", new AttributeValue().withS(topic));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#publishedOn", "publishedOn");
        expNames.put("#topic", "topic");

        DynamoDBQueryExpression<Stock> queryExpression = new DynamoDBQueryExpression<Stock>()
                .withIndexName("insightGSI")
                .withConsistentRead(false)
                .withKeyConditionExpression("#topic = :topic and #publishedOn >= :startsFrom")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);

        return dynamo.query(Stock.class, queryExpression);
    }

    @Override
    public Stock findBy(String code) {
        return dynamo.load(Stock.class, code);
    }

    @Override
    public List<Stock> findAll() {
        return dynamo.scan(Stock.class,new DynamoDBScanExpression());
    }

    @Override
    public Stock save(Stock Stock) {
        dynamo.save(Stock);
        return Stock;
    }
}