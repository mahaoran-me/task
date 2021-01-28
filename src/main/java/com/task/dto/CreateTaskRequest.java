package com.task.dto;

import lombok.Data;

@Data
public class CreateTaskRequest {
    private String limit;
    private String title;
}
