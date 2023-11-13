package com.tekcapzule.insight.application.function.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapzule.insight.domain.model.Topic;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class CreateNewsInput {
    private String title;
    private String description;
    private Topic topic;
    private List<String> tags;
    private String publishedOn;
}