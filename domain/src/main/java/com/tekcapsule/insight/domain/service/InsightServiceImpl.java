package com.tekcapsule.insight.domain.service;

import com.tekcapsule.insight.domain.command.CreateCommand;
import com.tekcapsule.insight.domain.command.UpdateCommand;
import com.tekcapsule.insight.domain.model.Insights;
import com.tekcapsule.insight.domain.repository.InsightRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class InsightServiceImpl implements InsightService {
    private InsightRepository insightRepository;

    @Autowired
    public InsightServiceImpl(InsightRepository insightRepository) {
        this.insightRepository = insightRepository;
    }

    @Override
    public void create(CreateCommand createCommand) {

        log.info(String.format("Entering create course service - Module Code :%s", createCommand.getTopicCode()));

        Insights insights = Insights.builder()
                .title(createCommand.getTitle())
                .topicCode(createCommand.getTopicCode())
                .author(createCommand.getAuthor())
                .publisher(createCommand.getPublisher())
                .duration(createCommand.getDuration())
                .courseUrl(createCommand.getCourseUrl())
                .summary(createCommand.getSummary())
                .description(createCommand.getDescription())
                .modules(createCommand.getModules())
                .prizingModel(createCommand.getPrizingModel())
                .deliveryMode(createCommand.getDeliveryMode())
                .learningMode(createCommand.getLearningMode())
                .imageUrl(createCommand.getImageUrl())
                .promotion(createCommand.getPromotion())
                .status(Status.ACTIVE)
                .build();

        insights.setAddedOn(createCommand.getExecOn());
        insights.setAddedBy(createCommand.getExecBy().getUserId());

        insightRepository.save(insights);
    }

    @Override
    public void update(UpdateCommand updateCommand) {

        log.info(String.format("Entering update course service - Course ID:%s", updateCommand.getCourseId()));

        Insights insights = insightRepository.findBy(updateCommand.getCourseId());
        if (insights != null) {
            insights.setTitle(updateCommand.getTitle());
            insights.setTopicCode(updateCommand.getTopicCode());
            insights.setAuthor(updateCommand.getAuthor());
            insights.setPublisher(updateCommand.getPublisher());
            insights.setDuration(updateCommand.getDuration());
            insights.setCourseUrl(updateCommand.getCourseUrl());
            insights.setSummary(updateCommand.getSummary());
            insights.setDescription(updateCommand.getDescription());
            insights.setModules(updateCommand.getModules());
            insights.setPrizingModel(updateCommand.getPrizingModel());
            insights.setDeliveryMode(updateCommand.getDeliveryMode());
            insights.setLearningMode(updateCommand.getLearningMode());
            insights.setPromotion(updateCommand.getPromotion());
            insights.setImageUrl(updateCommand.getImageUrl());
            insights.setUpdatedOn(updateCommand.getExecOn());
            insights.setUpdatedBy(updateCommand.getExecBy().getUserId());
            insightRepository.save(insights);
        }
    }

   /* @Override
    public void disable(DisableCommand disableCommand) {

        log.info(String.format("Entering disable topic service - Module Code:%s", disableCommand.getCode()));

        insightRepository.findBy(disableCommand.getCode());
        Module topic = insightRepository.findBy(disableCommand.getCode());
        if (topic != null) {
            topic.setStatus("INACTIVE");
            topic.setUpdatedOn(disableCommand.getExecOn());
            topic.setUpdatedBy(disableCommand.getExecBy().getUserId());
            insightRepository.save(topic);
        }
    }*/

    @Override
    public List<Insights> findAll() {

        log.info("Entering findAll Course service");

        return insightRepository.findAll();
    }

    @Override
    public List<Insights> findAllByTopicCode(String topicCode) {

        log.info(String.format("Entering findAllByTopicCode Course service - Module code:%s", topicCode));

        return insightRepository.findAllByTopicCode(topicCode);
    }


}
