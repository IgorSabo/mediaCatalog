package com.catalog.business.jobs;

/**
 * Created by Gile on 5/31/2018.
 */
public enum JobStatus {

    RUNNING("RUNNING"),
    SKIPPED("SKIPPED"),
    FINISHED("FINISHED"),
    FAILED("FAILED");

    private String name;

    JobStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
