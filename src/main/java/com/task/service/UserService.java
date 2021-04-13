package com.task.service;

import com.task.dto.RegisterRequest;
import com.task.dto.SimpleResponse;
import com.task.dto.UpdateUserRequest;
import com.task.entity.User;

public interface UserService {

    /**
     * 注册用户
     * @param request 注册请求
     * @return 通用返回值
     */
    SimpleResponse register(RegisterRequest request);

    /**
     * 注销用户
     * @param id 用户id
     * @return 通用返回值
     */
    SimpleResponse unRegister(Integer id);

    /**
     * 修改用户信息
     * @param request 用户信息
     * @return 通用返回值
     */
    SimpleResponse updateInfo(UpdateUserRequest request);

    User findUserById(int id);
}
