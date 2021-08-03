package com.tsystems.simulator.repository;

import com.tsystems.simulator.model.QueueEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface QueueRepository extends CrudRepository<QueueEntity, Long> {

    @Query("SELECT count(q) > 0 from QueueEntity q where q.name = :name")
    boolean isExistQueueByName(@Param("name") String name);
}
