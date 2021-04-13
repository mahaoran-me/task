package com.task.dto;

import com.task.entity.Task;

public class DtoUtil {

    public static void updateTaskRequestToTask(UpdateTaskRequest request, Task task) {
        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        if (request.getContent() != null) {
            task.setContent(request.getContent());
        }
        if (request.getLevel() != null) {
            task.setLevel(request.getLevel());
        }
        if (request.getFinished() != null) {
            task.setFinished(request.getFinished());
        }
        if (request.getDeleted() != null) {
            task.setDeleted(request.getDeleted());
        }
        if (request.getStartTime() != null) {
            task.setStartTime(request.getStartTime());
        }
        if (request.getEndTime() != null) {
            task.setEndTime(request.getEndTime());
        }
        if (request.getDoneTime() != null) {
            task.setDoneTime(request.getDoneTime());
        }
    }
}
