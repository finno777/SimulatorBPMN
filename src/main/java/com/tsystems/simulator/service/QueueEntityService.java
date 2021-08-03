package com.tsystems.simulator.service;

import com.tsystems.simulator.model.QueueEntity;

public interface QueueEntityService {

    QueueEntity saveQueue(QueueEntity entity);

    Boolean isExistQueueByName(String name);

}
