package com.training.spring.bigcorp.model;

import java.time.Instant;

public class Measure {

    private Instant instant;

    private Integer valueInWatt;

    private Captor captor;

    public Measure(Instant instant, Integer valueInWatt, Captor captor){
        this.instant = instant;
        this.valueInWatt = valueInWatt;
        this.captor = captor;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public Integer getValueInWatt() {
        return valueInWatt;
    }

    public void setValueInWatt(Integer valueInWatt) {
        this.valueInWatt = valueInWatt;
    }

    public Captor getCaptor() {
        return captor;
    }

    public void setCaptor(Captor captor) {
        this.captor = captor;
    }

    @Override
    public String toString(){
        return "Mesure effectuée a l'instant " + this.instant + "par le capteur : " + captor + " : " + valueInWatt + ".";
    }

    @Override
    public boolean equals(Object measure){
        Measure tempMeasure;
        tempMeasure = (Measure)measure;
        return this.valueInWatt == tempMeasure.getValueInWatt();
    }

    @Override
    public int hashCode(){
        return valueInWatt.hashCode();
    }
}
