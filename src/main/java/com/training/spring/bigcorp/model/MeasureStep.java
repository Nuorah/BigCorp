package com.training.spring.bigcorp.model;

public enum MeasureStep {
    ONE_MINUTE(60),
    FIFTEEN_MINUTES(60*15),
    THIRTY_MINUTES(60*30),
    ONE_HOUR(60*60),
    ONE_DAY(60*60*24);

    private int durationInSeconds;

    MeasureStep(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public int getDurationInSeconds(){
        return this.durationInSeconds;
    }
}
