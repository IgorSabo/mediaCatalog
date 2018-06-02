package com.catalog.business.systemProcesses;

/**
 * Created by Gile on 5/31/2018.
 */
public enum ProcessStatus {

    RUNNING("RUNNING"),
    FINISHED("FINISHED"),
    FAIL("FAIL");

    private String name;

    ProcessStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
