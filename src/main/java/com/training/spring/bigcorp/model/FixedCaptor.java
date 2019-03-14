package com.training.spring.bigcorp.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("FIXED")
public class FixedCaptor extends Captor {

    @NotNull
    @Min(0)
    private Integer defaultPowerInWatt;

    @Deprecated
    public FixedCaptor(){

    }

    public FixedCaptor(String name, Site site){
        super(name, site);
    }
}
