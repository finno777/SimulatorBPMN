package com.tsystems.simulator.service.impl;

import com.tsystems.simulator.model.QueueEntity;
import com.tsystems.simulator.service.RabbitService;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Service;

@Service
public class RabbitServiceImpl implements RabbitService {

    private final AmqpAdmin admin;

    public RabbitServiceImpl(AmqpAdmin admin) {
        this.admin = admin;
    }

    public void createQueue(QueueEntity q) {
        Queue queue = new Queue(q.getName(), false, false, false);
        Binding binding = new Binding(q.getName(), Binding.DestinationType.QUEUE, q.getExchange(), q.getRoutingKey(), null);
        admin.declareQueue(queue);
        admin.declareBinding(binding);
    }

}
