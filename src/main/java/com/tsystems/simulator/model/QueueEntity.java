package com.tsystems.simulator.model;


import com.tsystems.simulator.model.enumPackage.QueueType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "exchange")
    private String exchange;

    @Column(name = "routing_key")
    private String routingKey;

    @Column(name = "created")
    private Boolean created;

    @Enumerated(EnumType.STRING)
    @Column(name = "queue_type")
    private QueueType queueType;

}
