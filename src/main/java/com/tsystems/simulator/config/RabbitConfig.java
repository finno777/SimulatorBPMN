package com.tsystems.simulator.config;

import com.rabbitmq.http.client.Client;
import com.rabbitmq.http.client.ClientParameters;
import com.tsystems.simulator.config.props.RabbitClient;
import com.tsystems.simulator.config.props.RabbitQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

@Component
@Configuration
@RequiredArgsConstructor
public class RabbitConfig {

    private final RabbitClient client;
    private final RabbitQueue rabbitQueue;

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(client.getHost(), client.getPort());
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Client rabbitClient() throws MalformedURLException, URISyntaxException {
        return new Client(
                new ClientParameters()
                        .url("http://127.0.0.1:15671/api/")
                        .username(client.getUsername())
                        .password(client.getPassword())
        );
    }

    @Bean
    Queue queue() {
        return new Queue(rabbitQueue.getQueueName(), false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(rabbitQueue.getExchangeName());
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(rabbitQueue.getRoutingKey());
    }

}
