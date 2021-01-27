package com.task.service;

import com.task.dto.RegisterRequest;
import com.task.dto.SimpleResponse;
import com.task.dto.UpdateUserRequest;

public interface UserService {

    SimpleResponse register(RegisterRequest request);

    SimpleResponse unRegister(Integer id);

    SimpleResponse updateInfo(UpdateUserRequest request);
}
