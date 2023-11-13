package com.tekcapzule.insight.domain.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapzule.core.domain.Command;
import com.tekcapzule.insight.domain.model.*;
import com.tekcapzule.insight.domain.model.Topic;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CreateNewsCommand extends Command {
    private String title;
    private String description;
    private Topic topic;
    private List<String> tags;
    private String publishedOn;
}