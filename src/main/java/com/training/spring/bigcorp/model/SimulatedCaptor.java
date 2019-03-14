package com.training.spring.bigcorp.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SIMULATED")
public class SimulatedCaptor extends Captor {

    @Column
    private Integer minPowerInWatt;

    @Column
    private Integer maxPowerInWatt;

    @Deprecated
    public SimulatedCaptor(){

    }

    public SimulatedCaptor(String name, Site site){
        super(name, site);
    }
}
