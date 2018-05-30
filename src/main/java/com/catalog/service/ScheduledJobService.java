package com.catalog.service;

import com.catalog.model.entities.ScheduledJob;

/**
 * Created by Gile on 5/26/2018.
 */
public interface ScheduledJobService {

    void saveScheduledJob(ScheduledJob job);

    ScheduledJob findLastScheduledJob( Enum jobType );
}
