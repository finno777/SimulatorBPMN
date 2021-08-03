package com.tsystems.simulator.service;

import com.tsystems.simulator.model.QueueManager;

import java.util.List;

public interface QueueManagerService {
    void saveQueueManager(QueueManager manager);

    QueueManager getManagerByQueueOutName(String name);

    List<QueueManager> getManagerByQueueInName(String name);
}
