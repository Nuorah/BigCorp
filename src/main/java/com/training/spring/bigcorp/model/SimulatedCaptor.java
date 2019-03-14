package com.training.spring.bigcorp.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("SIMULATED")
public class SimulatedCaptor extends Captor {

    @NotNull
    @Min(0)
    private Integer minPowerInWatt;

    @NotNull
    @Min(0)
    private Integer maxPowerInWatt;

    @Deprecated
    public SimulatedCaptor(){

    }

    public SimulatedCaptor(String name, Site site){
        super(name, site);
    }

    @AssertTrue(message = "minPowerInWatt should be less than maxPowerInWatt")
    public boolean isValid(){
        return this.minPowerInWatt <= this.maxPowerInWatt;
    }
}
