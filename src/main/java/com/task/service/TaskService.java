package com.task.service;

import com.task.entity.Task;
import java.util.List;

public interface TaskService {

    Task findById(int id);

    List<Task> findByUid(int uid);

    List<Task> findInToday(int uid);

    List<Task> findInWeek(int uid);

    List<Task> findInMonth(int uid);

    List<Task> findFinished(int uid);

    List<Task> findTimeout(int uid);

    List<Task> findDeleted(int uid);

    void createTask(Task task);

    void updateTask(Task task);

    void deleteTask(int id);
}
