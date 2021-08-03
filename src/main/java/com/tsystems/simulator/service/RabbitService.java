package com.tsystems.simulator.service;

import com.tsystems.simulator.model.QueueEntity;

public interface RabbitService {

    void createQueue(QueueEntity q);

}
