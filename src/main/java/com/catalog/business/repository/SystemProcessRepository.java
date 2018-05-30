package com.catalog.business.repository;

import com.catalog.model.entities.SystemProcess;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Gile on 5/31/2018.
 */
@Transactional
public interface SystemProcessRepository extends CrudRepository<SystemProcess, Long> {

    SystemProcess findFirstByProcessTypeOrderByStartTimeDesc( Enum processType );
}
