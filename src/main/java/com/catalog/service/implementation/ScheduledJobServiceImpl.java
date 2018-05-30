package com.catalog.service.implementation;

import com.catalog.business.repository.ScheduledJobRepository;
import com.catalog.model.entities.ScheduledJob;
import com.catalog.service.ScheduledJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Gile on 5/26/2018.
 */
@Service
@Transactional
public class ScheduledJobServiceImpl implements  ScheduledJobService{

    @Autowired
    ScheduledJobRepository scheduledJobRepository;

    @Override
    public void saveScheduledJob(ScheduledJob job) {
        scheduledJobRepository.save(job);
    }

    @Override
    public ScheduledJob findLastScheduledJob(Enum jobType) {
       return scheduledJobRepository.findFirstByJobTypeOrderByStartTimeDesc(jobType);
    }
}
