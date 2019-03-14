package com.training.spring.bigcorp.model;

import org.springframework.stereotype.Repository;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("REAL")
public class RealCaptor extends Captor {

    @Deprecated
    public RealCaptor(){

    }

    public RealCaptor(String name, Site site){
        super(name, site);
    }
}
