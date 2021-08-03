package com.tsystems.simulator.repository;

import com.tsystems.simulator.model.MatcherProperties;
import com.tsystems.simulator.model.QueueEntity;
import com.tsystems.simulator.model.QueueManager;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatcherPropertiesRepository extends CrudRepository<MatcherProperties, Long> {

    @Override
    List<MatcherProperties> findAll();

    @Query("SELECT mp FROM MatcherProperties mp where mp.queueEntity = :qOut")
    List<MatcherProperties> findMatcherPropertiesByQueueOutEntity(@Param("qOut") QueueEntity entity);

}
