package com.task.service.impl;

import com.task.entity.Task;
import com.task.mapper.TaskMapper;
import com.task.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public Task findById(int id) {
        return taskMapper.selectById(id);
    }

    @Override
    public List<Task> findByUid(int uid) {
        return taskMapper.selectByUid(uid);
    }

    @Override
    public List<Task> findInToday(int uid) {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        List<Task> tasks = taskMapper.selectByDay(uid, today, tomorrow);
        timeoutFilter(tasks);
        return tasks;
    }

    @Override
    public List<Task> findInWeek(int uid) {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        LocalDate nextMonday = monday.plusDays(7);
        List<Task> tasks = taskMapper.selectByDay(uid, monday, nextMonday);
        timeoutFilter(tasks);
        return tasks;
    }

    @Override
    public List<Task> findInMonth(int uid) {
        LocalDate today = LocalDate.now();
        LocalDate firstDay = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate nextFirstDay = today.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1);
        List<Task> tasks = taskMapper.selectByDay(uid, firstDay, nextFirstDay);
        timeoutFilter(tasks);
        return tasks;
    }

    @Override
    public List<Task> findFinished(int uid) {
        return taskMapper.selectFinished(uid);
    }

    @Override
    public List<Task> findTimeout(int uid) {
        return taskMapper.selectTimeout(uid);
    }

    @Override
    public List<Task> findDeleted(int uid) {
        return taskMapper.selectDeleted(uid);
    }

    @Override
    public void createTask(Task task) {
        taskMapper.insert(task);
    }

    @Override
    public void updateTask(Task task) {
        taskMapper.update(task);
    }

    @Override
    public void deleteTask(int id) {
        taskMapper.delete(id);
    }

    private void timeoutFilter(List<Task> tasks) {
        LocalDateTime now = LocalDateTime.now();
        for (var task : tasks) {
            if (now.isAfter(task.getEndTime())) {
                taskMapper.timeout(task.getId());
                task.setTimeout(true);
            }
        }
    }
}
