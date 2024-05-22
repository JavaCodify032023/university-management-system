package com.example.universitymanagementsystem.service.impl;

import com.example.universitymanagementsystem.service.TaskSchedulingService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
public class TaskSchedulingServiceImpl implements TaskSchedulingService {
    private final TaskScheduler taskScheduler;

    private Map<String, ScheduledFuture<?>> jobsMap = new HashMap<>();

    public  String scheduleTaskWithFixedDelay(Runnable task, LocalDateTime startDate,Duration duration){
        String jobId = UUID.randomUUID().toString();
        ScheduledFuture<?> scheduledTask =
                taskScheduler.scheduleWithFixedDelay(task, startDate.toInstant(ZoneOffset.UTC),duration);
        jobsMap.put(jobId,scheduledTask);
        return jobId;
    }

    public Boolean removeTask(String jobId){
        return jobsMap.get(jobId).cancel(true);
    }

}
