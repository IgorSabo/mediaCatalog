package com.catalog.model.entities;

import com.catalog.business.jobs.JobType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Gile on 5/26/2018.
 */
@Entity
@Getter
@Setter
public class ScheduledJob {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private JobType jobType;

    private Date startTime;

    private Date endTime;

    private int titlesInserted;

    @Override
    public String toString() {
        return "ScheduledJob{" +
                "id=" + id +
                ", jobType=" + jobType +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", titlesInserted=" + titlesInserted +
                '}';
    }
}
