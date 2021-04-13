package com.task.mapper;

import com.task.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    User selectById(int id);

    @Select("select * from user where username = #{username}")
    User selectByUsername(String username);

    @Insert("insert into user values(default, #{username}, #{password}, #{phone}, #{email}, default, default)")
    void insert(User user);

    @Update("update user set username = #{username}, password = #{password}, phone = #{phone}, email = #{email} where id = #{id}")
    void update(User user);

    @Delete("delete from user where id = #{id}")
    void delete(int id);
}
