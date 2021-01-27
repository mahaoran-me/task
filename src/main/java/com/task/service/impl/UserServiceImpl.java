package com.task.service.impl;

import com.task.dto.RegisterRequest;
import com.task.dto.SimpleResponse;
import com.task.dto.UpdateUserRequest;
import com.task.entity.User;
import com.task.mapper.UserMapper;
import com.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public SimpleResponse register(RegisterRequest request) {
        User user = userMapper.selectByUsername(request.getUsername());
        if (user != null) {
            return new SimpleResponse(0, "用户名已被注册");
        }
        user = new User(null, request.getUsername(), DigestUtils.md5DigestAsHex(request.getPassword().getBytes()),
                request.getPhone(), request.getEmail());
        userMapper.insert(user);
        return new SimpleResponse(1, "注册成功");
    }

    @Override
    public SimpleResponse unRegister(Integer id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return new SimpleResponse(0, "无此用户");
        }
        userMapper.delete(id);
        return new SimpleResponse(1, "注销成功");
    }

    @Override
    public SimpleResponse updateInfo(UpdateUserRequest request) {
        User user = userMapper.selectById(request.getId());
        if (user == null) {
            return new SimpleResponse(0, "无此用户");
        }
        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }
        if (request.getPassword() != null) {
            user.setPassword(DigestUtils.md5DigestAsHex(request.getPassword().getBytes()));
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        userMapper.update(user);
        return new SimpleResponse(1, "修改成功");
    }
}
