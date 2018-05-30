package com.catalog.model.entities;

import com.catalog.business.systemProcesses.ProcessStatus;
import com.catalog.business.systemProcesses.ProcessType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Gile on 5/31/2018.
 */
@Entity
@Getter
@Setter
public class SystemProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProcessType processType;

    private Date startTime;

    private Date endTime;

    @Enumerated(EnumType.STRING)
    private ProcessStatus processStatus;

}
