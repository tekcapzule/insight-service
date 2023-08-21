package com.tekcapsule.insight.domain.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.core.domain.Command;
import com.tekcapsule.insight.domain.model.*;
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