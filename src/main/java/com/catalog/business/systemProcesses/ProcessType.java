package com.catalog.business.systemProcesses;

/**
 * Created by Gile on 5/31/2018.
 */
public enum ProcessType {

    SYNCHRONIZATION_PROCESS("synchronizationProcess");

    private String name;

    ProcessType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
