package com.tsystems.simulator.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("rabbitmq")
public class RabbitClient {
    private String host;
    private int port;
    private String username;
    private String password;
}
