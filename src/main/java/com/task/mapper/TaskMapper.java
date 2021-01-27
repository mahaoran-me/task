package com.task.mapper;

import com.task.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskMapper {

    @Select("select * from task where id = #{id}")
    Task selectById(int id);

    @Select("select * from task where uid = #{uid}")
    List<Task> selectTasksByUid(int uid);

}
