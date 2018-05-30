package com.catalog.business.repository;

import com.catalog.model.entities.ScheduledJob;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Gile on 5/26/2018.
 */
@Transactional
public interface ScheduledJobRepository extends CrudRepository<ScheduledJob, Long> {

    ScheduledJob findFirstByJobTypeOrderByStartTimeDesc( Enum jobType );
}
