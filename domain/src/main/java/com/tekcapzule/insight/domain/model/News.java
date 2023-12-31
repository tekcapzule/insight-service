package com.tekcapzule.insight.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapzule.core.domain.AggregateRoot;
import com.tekcapzule.core.domain.BaseDomainEntity;
import lombok.*;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@DynamoDBTable(tableName = "Insight")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class News extends BaseDomainEntity implements AggregateRoot {
    @DynamoDBHashKey(attributeName="insightId")
    @DynamoDBAutoGeneratedKey
    private String insightId;
    @DynamoDBAttribute(attributeName = "title")
    private String title;
    @DynamoDBAttribute(attributeName = "description")
    private String description;
    @DynamoDBAttribute(attributeName = "topic")
    @DynamoDBTypeConvertedEnum
    private Topic topic;
    @DynamoDBAttribute(attributeName = "tags")
    private List<String> tags;
    @DynamoDBAttribute(attributeName = "publishedOn")
    private String publishedOn;
    @DynamoDBAttribute(attributeName = "status")
    @DynamoDBTypeConvertedEnum
    private Status status;
}
