package com.training.spring.bigcorp.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.Instant;

@Entity
public class Measure {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;

    @NotNull
    @Past
    private Instant instant;

    @NotNull
    @Min(0)
    private Integer valueInWatt;

    @ManyToOne(optional = false)
    private Captor captor;

    public Measure(){

    }


    public Measure(Instant instant, Integer valueInWatt, Captor captor){
        this.instant = instant;
        this.valueInWatt = valueInWatt;
        this.captor = captor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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
