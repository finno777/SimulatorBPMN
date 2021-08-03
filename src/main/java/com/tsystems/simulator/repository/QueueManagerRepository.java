package com.tsystems.simulator.repository;

import com.tsystems.simulator.model.QueueManager;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QueueManagerRepository extends CrudRepository<QueueManager, Long> {

    @Query("SELECT q FROM QueueManager q where q.qOut.name= :qOut")
    QueueManager findQueueOutByName(@Param("qOut") String queueName);

    @Query("SELECT q FROM QueueManager q where q.qIn.name= :qIn")
    List<QueueManager> findQueueInByName(@Param("qIn") String queueName);
}
