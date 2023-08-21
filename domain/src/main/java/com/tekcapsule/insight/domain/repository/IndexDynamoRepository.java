package com.tekcapsule.insight.domain.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.tekcapsule.insight.domain.model.IndexRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository
public class IndexDynamoRepository implements IndexRepository {
    private DynamoDBMapper dynamo;
    public static final String ACTIVE_STATUS = "ACTIVE";

    @Autowired
    public IndexDynamoRepository(DynamoDBMapper dynamo) {
        this.dynamo = dynamo;
    }

    @Override
    public List<IndexRecord> findAll(String startsFrom) {
        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":status", new AttributeValue().withS(ACTIVE_STATUS));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#status", "status");

        DynamoDBQueryExpression<IndexRecord> queryExpression = new DynamoDBQueryExpression<IndexRecord>()
                .withIndexName("topicGSI").withConsistentRead(false)
                .withKeyConditionExpression("#status = :status and #topicCode = :topicCode")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);

        return dynamo.query(IndexRecord.class, queryExpression);
    }

    @Override
    public IndexRecord findBy(String code) {
        return dynamo.load(IndexRecord.class, code);
    }

    @Override
    public List<IndexRecord> findAll() {
        return dynamo.scan(IndexRecord.class,new DynamoDBScanExpression());
    }

    @Override
    public IndexRecord save(IndexRecord insights) {
        dynamo.save(insights);
        return insights;
    }
}