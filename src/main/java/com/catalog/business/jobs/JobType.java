package com.catalog.business.jobs;

/**
 * Created by Gile on 5/26/2018.
 */
public enum JobType {

    SYNCHRONIZATION_JOB("synchronizationJob"),
    NEW_TORRENT_PROCESSING("newTorrentProcessing");

    private String name;

    JobType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
