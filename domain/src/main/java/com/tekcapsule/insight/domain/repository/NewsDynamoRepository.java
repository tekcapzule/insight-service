package com.tekcapsule.insight.domain.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.tekcapsule.insight.domain.model.News;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository
public class NewsDynamoRepository implements NewsRepository{
    private DynamoDBMapper dynamo;
    public static final String ACTIVE_STATUS = "ACTIVE";

    @Autowired
    public NewsDynamoRepository(DynamoDBMapper dynamo) {
        this.dynamo = dynamo;
    }

    @Override
    public List<News> findAll(String startsFrom, String topic) {

        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":startsFrom", new AttributeValue().withS(startsFrom));
        expAttributes.put(":topic", new AttributeValue().withS(topic));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#publishedOn", "publishedOn");
        expNames.put("#topic", "topic");

        DynamoDBQueryExpression<News> queryExpression = new DynamoDBQueryExpression<News>()
                .withConsistentRead(false)
                .withKeyConditionExpression("#topic = :topic and #publishedOn >= :startsFrom")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);

        return dynamo.query(News.class, queryExpression);
    }

    @Override
    public News findBy(String code) {
        return dynamo.load(News.class, code);
    }

    @Override
    public List<News> findAll() {
        return dynamo.scan(News.class,new DynamoDBScanExpression());
    }

    @Override
    public News save(News news) {
        dynamo.save(news);
        return news;
    }
}