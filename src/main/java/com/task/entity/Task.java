package com.task.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Task {
    private Integer id;
    private Integer uid;
    private String title;
    private String Content;
    private Integer level;
    private Boolean finished;
    private Boolean timeout;
    private Boolean deleted;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime doneTime;
}