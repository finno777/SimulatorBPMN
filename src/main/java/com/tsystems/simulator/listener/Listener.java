package com.tsystems.simulator.listener;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.tsystems.simulator.config.props.RabbitQueue;
import com.tsystems.simulator.model.MatcherProperties;
import com.tsystems.simulator.model.QueueEntity;
import com.tsystems.simulator.model.QueueManager;
import com.tsystems.simulator.model.enumPackage.PredicateType;
import com.tsystems.simulator.service.MatcherPropertiesService;
import com.tsystems.simulator.service.QueueManagerService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableRabbit
@Component
@RequiredArgsConstructor
public class Listener {

    @Autowired
    private final RabbitTemplate template;
    @Autowired
    private QueueManagerService queueManagerService;
    @Autowired
    private MatcherPropertiesService matcherPropertiesService;
    @Autowired
    private RabbitQueue rabbitQueue;
    @Autowired
    private RuntimeService runtimeService;



    public void worker1(String message) {
        Object jsonDocument = Configuration.defaultConfiguration()
                .jsonProvider()
                .parse(message);
        try {
            List<QueueManager> managerByQueueInName = queueManagerService.getManagerByQueueInName(rabbitQueue.getQueueName());
            managerByQueueInName.forEach(queueOutManager -> {
                PredicateType predicateType = queueOutManager.getPredicateType();
                QueueEntity qOut = queueOutManager.getQOut();
                List<MatcherProperties> allPropertiesByQueueOut = matcherPropertiesService.findAllPropertiesByQueueOut(qOut);
                Map<String, String> mapJsonValueAndValueMatcher = new HashMap<>();
                allPropertiesByQueueOut.forEach(mp -> {
                    String valueIn = JsonPath.parse(jsonDocument).read(mp.getJsonPath());
                    String valueMatcher = mp.getValueMatcher();
                    mapJsonValueAndValueMatcher.put(valueIn, valueMatcher);
                });
                if (predicateType.execute(mapJsonValueAndValueMatcher)) {
                    template.convertAndSend(qOut.getName(), queueOutManager.getMessage());
                }
            });
        } catch (Exception ignored) {
        }
    }

    @RabbitListener(queues = "${queue.queueName}")
    public void worker2(String message) {
        Object json = Configuration.defaultConfiguration()
                .jsonProvider()
                .parse(message);
        Map<String, Object> variables = new HashMap<>();
        try {
            String value = JsonPath.parse(json).read("$.activityId");
            System.out.println(this.getClass().getName() + value);
            variables.put("message", value);
            runtimeService.startProcessInstanceByKey("Process_088n9s9",
                    "12345", variables);
        } catch (Exception ignored) {
        }
    }
}
