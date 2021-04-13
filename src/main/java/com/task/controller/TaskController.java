package com.task.controller;

import com.task.dto.*;
import com.task.entity.Task;
import com.task.entity.User;
import com.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/task/all")
    public List<Task> all(HttpSession session) {
        User user = (User) session.getAttribute("localUser");
        return taskService.findInAll(user.getId());
    }

    @GetMapping("/task/today")
    public List<Task> today(HttpSession session) {
        User user = (User) session.getAttribute("localUser");
        return taskService.findInToday(user.getId());
    }

    @GetMapping("/task/week")
    public List<Task> week(HttpSession session) {
        User user = (User) session.getAttribute("localUser");
        return taskService.findInWeek(user.getId());
    }

    @GetMapping("/task/month")
    public List<Task> month(HttpSession session) {
        User user = (User) session.getAttribute("localUser");
        return taskService.findInMonth(user.getId());
    }

    @GetMapping("/task/finished")
    public List<Task> finished(HttpSession session) {
        User user = (User) session.getAttribute("localUser");
        return taskService.findFinished(user.getId());
    }

    @GetMapping("/task/timeout")
    public List<Task> timeout(HttpSession session) {
        User user = (User) session.getAttribute("localUser");
        return taskService.findTimeout(user.getId());
    }

    @GetMapping("/task/deleted")
    public List<Task> deleted(HttpSession session) {
        User user = (User) session.getAttribute("localUser");
        return taskService.findDeleted(user.getId());
    }

    @PostMapping("/task/create")
    public SimpleResponse create(@RequestBody CreateTaskRequest request, HttpSession session) {
        User user = (User) session.getAttribute("localUser");
        Task task = new Task();
        task.setUid(user.getId());
        task.setTitle(request.getTitle());
        task.setLevel(1);
        if ("today".equals(request.getLimit())) {
            task.setStartTime(LocalDateTime.now());
            task.setEndTime(LocalDateTime.of(LocalDate.now(), LocalTime.MAX).minusSeconds(1));
        } else if ("week".equals(request.getLimit())) {
            LocalDate today = LocalDate.now();
            LocalDate sunday = today.with(DayOfWeek.SUNDAY);
            task.setStartTime(LocalDateTime.now());
            task.setEndTime(LocalDateTime.of(sunday, LocalTime.MAX).minusSeconds(1));
        } else if ("month".equals(request.getLimit())) {
            LocalDate today = LocalDate.now();
            LocalDate lastOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
            task.setStartTime(LocalDateTime.now());
            task.setEndTime(LocalDateTime.of(lastOfMonth, LocalTime.MAX).minusSeconds(1));
        }
        taskService.createTask(task);
        return new SimpleResponse(1, "创建成功");
    }

    @PostMapping("/task/update")
    public GenericResponse<Task> update(@RequestBody UpdateTaskRequest request, HttpSession session) {
        Task task = taskService.findById(request.getId());
        DtoUtil.updateTaskRequestToTask(request, task);
        task = taskService.updateTask(task);
        return new GenericResponse<Task>(1, "修改成功", task);
    }

    @GetMapping("/task/delete/{id}")
    public SimpleResponse delete(@PathVariable int id) {
        Task task = taskService.findById(id);
        if (task == null) {
            return new SimpleResponse(0, "任务不存在");
        }
        taskService.deleteTask(id);
        return new SimpleResponse(1, "移入回收站");
    }

    @GetMapping("/task/undelete/{id}")
    public SimpleResponse undelete(@PathVariable int id) {
        Task task = taskService.findById(id);
        if (task == null) {
            return new SimpleResponse(0, "任务不存在");
        }
        taskService.unDeleteTask(id);
        return new SimpleResponse(1, "移出回收站");
    }
}
