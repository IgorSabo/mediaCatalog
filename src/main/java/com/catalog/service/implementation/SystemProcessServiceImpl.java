package com.catalog.service.implementation;

import com.catalog.business.repository.SystemProcessRepository;
import com.catalog.business.systemProcesses.ProcessStatus;
import com.catalog.business.systemProcesses.ProcessType;
import com.catalog.model.entities.SystemProcess;
import com.catalog.service.SystemProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * Created by Gile on 5/31/2018.
 */
@Repository
@Transactional
public class SystemProcessServiceImpl implements SystemProcessService {

    @Autowired
    SystemProcessRepository systemProcessRepository;

    @Override
    public SystemProcess findFirstByProcessTypeOrderByStartTimeDesc(Enum processType) {
        return systemProcessRepository.findFirstByProcessTypeOrderByStartTimeDesc(processType);
    }

    @Override
    public void saveSystemProcess(SystemProcess process) {
        systemProcessRepository.save(process);
    }

    @Override
    public SystemProcess saveSystemProcess(ProcessType processType) {
        SystemProcess syncProcess = new SystemProcess();
        syncProcess.setStartTime(new Date());
        syncProcess.setProcessStatus(ProcessStatus.RUNNING);
        syncProcess.setProcessType(processType);
        saveSystemProcess(syncProcess);
        return syncProcess;
    }
}
