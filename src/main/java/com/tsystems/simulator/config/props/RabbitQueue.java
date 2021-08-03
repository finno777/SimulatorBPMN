package com.tsystems.simulator.config.props;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("queue")
public class RabbitQueue {
    private String queueName;
    private String exchangeName;
    private String routingKey;
}
