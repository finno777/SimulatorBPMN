package com.tsystems.simulator.service.impl;

import com.tsystems.simulator.model.MatcherProperties;
import com.tsystems.simulator.model.QueueEntity;
import com.tsystems.simulator.repository.MatcherPropertiesRepository;
import com.tsystems.simulator.service.MatcherPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatcherPropertiesServiceImpl implements MatcherPropertiesService {

    @Autowired
    private MatcherPropertiesRepository matcherPropertiesRepository;

    @Override
    public void saveProperties(MatcherProperties matcherProperties) {
        matcherPropertiesRepository.save(matcherProperties);
    }

    @Override
    public void saveAllProperties(List<MatcherProperties> matcherProperties) {
        matcherPropertiesRepository.saveAll(matcherProperties);
    }

    @Override
    public List<MatcherProperties> findAllProperties() {
        return matcherPropertiesRepository.findAll();
    }

    @Override
    public List<MatcherProperties> findAllPropertiesByQueueOut(QueueEntity queueEntity) {
        return matcherPropertiesRepository.findMatcherPropertiesByQueueOutEntity(queueEntity);
    }
}
