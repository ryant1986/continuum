package com.continuum;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class JobInfo {
    private Long id;
    @NotNull
    private String target;
    private double mean;
    private double percentile90;
    private boolean isRegression;

    public JobInfo() {
        // Jackson deserialization
    }

    public JobInfo(long id, String target, double mean, double percentile90, boolean isRegression) {
        this.id = id;
        this.target = target;
        this.mean = mean;
        this.percentile90 = percentile90;
        this.isRegression = isRegression;

    }

    @JsonProperty
    public Long getId() {
        return id;
    }

    @JsonProperty
    public String getTarget() {
        return target;
    }

    @JsonProperty
    public double getMean() {
        return mean;
    }

    @JsonProperty
    public boolean getIsRegression() {
        return isRegression;
    }

    @Override
    public String toString() {
        return id + ":" + target + ":" + mean + ":" + isRegression;
    }

    public double getPercentile90() {
        return percentile90;
    }
}
