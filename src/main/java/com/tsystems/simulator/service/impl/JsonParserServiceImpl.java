package com.tsystems.simulator.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.tsystems.simulator.config.RabbitConfig;
import com.tsystems.simulator.config.props.RabbitQueue;
import com.tsystems.simulator.model.MatcherProperties;
import com.tsystems.simulator.model.QueueEntity;
import com.tsystems.simulator.model.QueueManager;
import com.tsystems.simulator.model.enumPackage.PredicateType;
import com.tsystems.simulator.model.enumPackage.QueueType;
import com.tsystems.simulator.service.JsonParserService;
import com.tsystems.simulator.service.MatcherPropertiesService;
import com.tsystems.simulator.service.QueueEntityService;
import com.tsystems.simulator.service.QueueManagerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JsonParserServiceImpl implements JsonParserService {

    private final RabbitConfig config;
    private final RabbitServiceImpl rabbitServiceImpl;
    private final QueueEntityService queueEntityService;
    private final QueueManagerService queueManagerService;
    private final RabbitQueue rabbitQueue;
    private final MatcherPropertiesService matcherPropertiesService;


    public JsonParserServiceImpl(RabbitConfig config,
                                 RabbitServiceImpl rabbitServiceImpl,
                                 QueueEntityService queueEntityService,
                                 QueueManagerService queueManagerService,
                                 RabbitQueue rabbitQueue,
                                 MatcherPropertiesService matcherPropertiesService) {
        this.config = config;
        this.rabbitServiceImpl = rabbitServiceImpl;
        this.queueEntityService = queueEntityService;
        this.queueManagerService = queueManagerService;
        this.rabbitQueue = rabbitQueue;
        this.matcherPropertiesService = matcherPropertiesService;
    }

    @Override
    public void mainParse(String json) {

        QueueEntity queueEntityIn = QueueEntity.builder()
                .name(rabbitQueue.getQueueName())
                .exchange(rabbitQueue.getExchangeName())
                .routingKey(rabbitQueue.getQueueName())
                .queueType(QueueType.QUEUE_IN).build();

        QueueEntity queueEntityOut = JsonPath.parse(json).read("$.queueOutProperties", QueueEntity.class);

        queueEntityOut.setQueueType(QueueType.QUEUE_OUT);
        //TODO if qIN is created
//        if(!queueEntityService.isExistQueueByName(queueEntityIn.getName())){
//
//        }

        QueueEntity savedQueueEntityIn = queueEntityService.saveQueue(queueEntityIn);
        QueueEntity savedQueueEntityOut = queueEntityService.saveQueue(queueEntityOut);

        Configuration conf = Configuration
                .builder()
                .mappingProvider(new JacksonMappingProvider())
                .jsonProvider(new JacksonJsonProvider())
                .build();

        List<MatcherProperties> propertiesList = JsonPath
                .using(conf)
                .parse(json)
                .read("$.queueInProperties", new TypeRef<List<MatcherProperties>>() {
                });

        propertiesList.forEach(mp -> mp.setQueueEntity(savedQueueEntityOut));
        matcherPropertiesService.saveAllProperties(propertiesList);

        String message = JsonPath.parse(json).read("$.message");

        String predicateProperties = JsonPath.parse(json).read("$.predicate");


        rabbitServiceImpl.createQueue(savedQueueEntityIn);
        if (savedQueueEntityOut.getCreated()) {
            rabbitServiceImpl.createQueue(savedQueueEntityOut);
        }

        QueueManager manager = QueueManager.builder()
                .qIn(savedQueueEntityIn)
                .qOut(savedQueueEntityOut)
                .message(message)
                .predicateType(PredicateType.valueOf(predicateProperties)).build();

        queueManagerService.saveQueueManager(manager);

    }

}
