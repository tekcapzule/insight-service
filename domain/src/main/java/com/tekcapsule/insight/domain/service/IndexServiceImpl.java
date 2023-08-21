package com.tekcapsule.insight.domain.service;

import com.sun.tools.javac.util.List;
import com.tekcapsule.insight.domain.command.CreateIndexRecordCommand;
import com.tekcapsule.insight.domain.command.UpdateIndexRecordCommand;
import com.tekcapsule.insight.domain.model.IndexRecord;
import com.tekcapsule.insight.domain.repository.IndexRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IndexServiceImpl implements IndexService {
    private IndexRepository indexRepository;

    @Autowired
    public IndexServiceImpl(IndexRepository indexRepository) {
        this.indexRepository = indexRepository;
    }

    @Override
    public void create(CreateIndexRecordCommand createIndexRecordCommand) {

        log.info(String.format("Entering create index service - Index Name :%s", createIndexRecordCommand.getStockIndex().getValue()));

        IndexRecord indexRecord = IndexRecord.builder()
                .stockIndex(createIndexRecordCommand.getStockIndex())
                .topic(createIndexRecordCommand.getTopic())
                .closingOn(createIndexRecordCommand.getClosingOn())
                .valueOnClosing(createIndexRecordCommand.getValueOnClosing())
                .stocks(createIndexRecordCommand.getStocks())
                .comment(createIndexRecordCommand.getComment())
                .build();

        indexRecord.setAddedOn(createIndexRecordCommand.getExecOn());
        indexRecord.setAddedBy(createIndexRecordCommand.getExecBy().getUserId());
        indexRepository.save(indexRecord);
    }

    @Override
    public void update(UpdateIndexRecordCommand updateIndexRecordCommand) {

        log.info(String.format("Entering update index service - Insight ID:%s", updateIndexRecordCommand.getInsightId()));

        IndexRecord indexRecord = IndexRepository.findBy(updateIndexRecordCommand.getInsightId());
        if (indexRecord != null) {
            indexRecord.setStockIndex(updateIndexRecordCommand.getStockIndex());
            indexRecord.setTopic(updateIndexRecordCommand.getTopic());
            indexRecord.setClosingOn(updateIndexRecordCommand.getClosingOn());
            indexRecord.setValueOnClosing(updateIndexRecordCommand.getValueOnClosing());
            indexRecord.setStockIndex(updateIndexRecordCommand.getStockIndex());
            indexRecord.setComment(updateIndexRecordCommand.getComment());
            indexRecord.setUpdatedOn(updateIndexRecordCommand.getExecOn());
            indexRecord.setUpdatedBy(updateIndexRecordCommand.getExecBy().getUserId());
            indexRepository.save(indexRecord);
        }
    }

    @Override
    public List<IndexRecord> findAll() {

        log.info("Entering findAll index service");

        return IndexRepository.findAll();
    }


}
