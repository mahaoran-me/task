package com.task.mapper;

import com.task.entity.Task;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface TaskMapper {

    @Select("select * from task where id = #{id}")
    Task selectById(int id);

    @Select("select * from task where uid = #{uid}")
    List<Task> selectByUid(int uid);

    @Select("select * from task where uid = #{uid} and deleted = 0 and end_time between #{start} and #{end}")
    List<Task> selectByDay(int uid, LocalDate start, LocalDate end);

    @Select("select * from task where uid = #{uid} and deleted = 0 and finished = 1")
    List<Task> selectFinished(int uid);

    @Select("select * from task where uid = #{uid} and deleted = 0 and timeout = 1")
    List<Task> selectTimeout(int uid);

    @Select("select * from task where uid = #{uid} and deleted = 1")
    List<Task> selectDeleted(int uid);

    @Insert("insert into task values (default, #{uid}, #{title}, #{content}, #{level}, #{startTime}, #{endTime}, #{doneTime}," +
            "default, default, default, default, default)")
    void insert(Task task);

    @Update("update task set title = #{title}, content = #{content}, level = #{level}, start_time = #{startTime}," +
            "end_time = #{endTime}, done_time = #{doneTime}, finished = #{finished}, timeout = #{timeout}" +
            "deleted = #{deleted} where id = #{id}")
    void update(Task task);

    @Delete("delete from task where id = #{id}")
    void delete(int id);

    @Update("update task set timeout = 1 where id = #{id}")
    void timeout(int id);
}
