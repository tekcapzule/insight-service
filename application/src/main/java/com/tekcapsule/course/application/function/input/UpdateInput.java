package com.tekcapsule.course.application.function.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.course.domain.model.*;
import com.tekcapsule.course.domain.model.Module;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class UpdateInput {
    private String courseId;
    private String title;
    private String topicCode;
    private String author;
    private String publisher;
    private String duration;
    private String courseUrl;
    private String summary;
    private String description;
    private List<Module> modules;
    private PrizingModel prizingModel;
    private DeliveryMode deliveryMode;
    private LearningMode learningMode;
    private String imageUrl;
    private Promotion promotion;
}
