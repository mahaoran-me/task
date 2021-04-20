package com.task.service;

import com.task.entity.Task;
import java.util.List;

public interface TaskService {

    /**
     * 根据id查询任务
     * @param id 用户id
     * @return 任务
     */
    Task findById(int id);

    /**
     * 根据用户id查询任务
     * @param uid 用户id
     * @return 任务列表
     */
    List<Task> findByUid(int uid);

    List<Task> findInAll(int uid);
    List<Task> searchInAll(int uid, String pattern);

    /**
     * 查询用户今日任务
     * @param uid 用户id
     * @return 任务列表
     */
    List<Task> findInToday(int uid);
    List<Task> searchInToday(int uid, String pattern);

    /**
     * 查询用户本周任务
     * @param uid 用户id
     * @return 任务列表
     */
    List<Task> findInWeek(int uid);
    List<Task> searchInWeek(int uid, String pattern);

    /**
     * 查询用户本月任务
     * @param uid 用户id
     * @return 任务列表
     */
    List<Task> findInMonth(int uid);
    List<Task> searchInMonth(int uid, String pattern);

    /**
     * 查询用户已完成任务
     * @param uid 用户id
     * @return 任务列表
     */
    List<Task> findFinished(int uid);
    List<Task> searchFinished(int uid, String pattern);

    /**
     * 查询用户超时任务
     * @param uid 用户id
     * @return 任务列表
     */
    List<Task> findTimeout(int uid);
    List<Task> searchTimeout(int uid, String pattern);

    /**
     * 查询用户已删除任务
     * @param uid 用户id
     * @return 任务列表
     */
    List<Task> findDeleted(int uid);
    List<Task> searchDeleted(int uid, String pattern);

    /**
     * 新建任务
     * @param task 任务
     */
    void createTask(Task task);

    /**
     * 更新任务
     * @param task 任务
     */
    Task updateTask(Task task);

    /**
     * 删除任务
     * @param id 任务id
     */
    void deleteTask(int id);

    void unDeleteTask(int id);
}
