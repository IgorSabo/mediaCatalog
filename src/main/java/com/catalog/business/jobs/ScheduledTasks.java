package com.catalog.business.jobs;
import com.catalog.business.systemProcesses.ProcessStatus;
import com.catalog.business.systemProcesses.ProcessType;
import com.catalog.business.utils.CntTablesManipulator;
import com.catalog.model.entities.ScheduledJob;
import com.catalog.model.entities.SystemProcess;
import com.catalog.service.ScheduledJobService;
import com.catalog.service.SystemProcessService;
import com.catalog.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by Gile on 5/26/2018.
 */
@Component
public class ScheduledTasks {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    ScheduledJobService scheduledJobService;

    @Autowired
    TitleService titleService;

    @Autowired
    private CntTablesManipulator cntTablesManipulator;

    @Autowired
    private SystemProcessService systemProcessService;

    @Scheduled(cron = "0 */5 * ? * *")
    public void scheduleTaskWithCronExpression() {
        System.out.println(MessageFormat.format("Cron Task :: Execution Time - {0}", dateTimeFormatter.format(LocalDateTime.now())));

        ScheduledJob job = new ScheduledJob();
        job.setJobType(JobType.SYNCHRONIZATION_JOB);
        job.setStartTime(new Date());

        Number oldCount = titleService.getTotalEntities();

        SystemProcess process = systemProcessService.findFirstByProcessTypeOrderByStartTimeDesc(ProcessType.SYNCHRONIZATION_PROCESS);

        //if synchronization is running in the background, skip this job
        if( process == null || process.getProcessStatus().equals(ProcessStatus.FINISHED) ){
            titleService.processNewTitles(null);
            job.setJobStatus(JobStatus.FINISHED);
        }else{
            job.setJobStatus(JobStatus.SKIPPED);
        }

        //set job end time
        job.setEndTime(new Date());

        Number newCount = titleService.getTotalEntities();

        //setting number of inserted entities
        job.setTitlesInserted(newCount.intValue() - oldCount.intValue());

        //updating count tables
        System.out.println("Updating count tables");
        cntTablesManipulator.updateCountTables();

        System.out.println("Synchronization job completed!");
        scheduledJobService.saveScheduledJob(job);
    }
}
