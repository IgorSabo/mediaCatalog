package com.catalog.service;

import com.catalog.business.systemProcesses.ProcessType;
import com.catalog.model.entities.SystemProcess;

/**
 * Created by Gile on 5/31/2018.
 */
public interface SystemProcessService {

    SystemProcess findFirstByProcessTypeOrderByStartTimeDesc(Enum processType );

    void saveSystemProcess( SystemProcess process );

    SystemProcess saveSystemProcess( ProcessType processType );
}
