package com.tsystems.simulator.service.impl;

import com.tsystems.simulator.model.QueueManager;
import com.tsystems.simulator.repository.QueueManagerRepository;
import com.tsystems.simulator.service.QueueManagerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueueManagerServiceImpl implements QueueManagerService {

    private final QueueManagerRepository queueManagerRepository;

    public QueueManagerServiceImpl(QueueManagerRepository queueManagerRepository) {
        this.queueManagerRepository = queueManagerRepository;
    }

    @Override
    public void saveQueueManager(QueueManager manager) {
        queueManagerRepository.save(manager);
    }

    @Override
    public QueueManager getManagerByQueueOutName(String name) {
        return queueManagerRepository.findQueueOutByName(name);
    }

    @Override
    public List<QueueManager> getManagerByQueueInName(String name) {
        return queueManagerRepository.findQueueInByName(name);
    }
}
