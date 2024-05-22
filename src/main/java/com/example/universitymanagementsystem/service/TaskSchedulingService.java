package com.example.universitymanagementsystem.service;

import java.time.Duration;
import java.time.LocalDateTime;

public interface TaskSchedulingService {
    String scheduleTaskWithFixedDelay(Runnable task, LocalDateTime startDate, Duration duration);
    Boolean removeTask(String jobId);
}
