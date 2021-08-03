package com.tsystems.simulator.service.impl;

import com.tsystems.simulator.model.QueueEntity;
import com.tsystems.simulator.repository.QueueRepository;
import com.tsystems.simulator.service.QueueEntityService;
import org.springframework.stereotype.Service;

@Service
public class QueueEntityServiceImpl implements QueueEntityService {

    private final QueueRepository queueRepository;

    public QueueEntityServiceImpl(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }

    @Override
    public QueueEntity saveQueue(QueueEntity entity) {
        return queueRepository.save(entity);
    }

    @Override
    public Boolean isExistQueueByName(String name) {
        return queueRepository.isExistQueueByName(name);
    }
}
