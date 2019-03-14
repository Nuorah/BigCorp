package com.training.spring.bigcorp.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FIXED")
public class FixedCaptor extends Captor {

    @Column
    private Integer defaultPowerInWatt;

    @Deprecated
    public FixedCaptor(){

    }

    public FixedCaptor(String name, Site site){
        super(name, site);
    }
}
