package com.tsystems.simulator.service;

import com.tsystems.simulator.model.MatcherProperties;
import com.tsystems.simulator.model.QueueEntity;

import java.util.List;

public interface MatcherPropertiesService {

    void saveProperties(MatcherProperties matcherProperties);

    void saveAllProperties(List<MatcherProperties> matcherProperties);

    List<MatcherProperties> findAllProperties();

    List<MatcherProperties> findAllPropertiesByQueueOut(QueueEntity queueEntity);
}
