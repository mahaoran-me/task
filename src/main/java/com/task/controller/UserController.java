package com.task.controller;

import com.task.dto.RegisterRequest;
import com.task.dto.SimpleResponse;
import com.task.dto.UpdateUserRequest;
import com.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public SimpleResponse register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @GetMapping("/unRegister/{id}")
    public SimpleResponse unRegister(@PathVariable int id) {
        return userService.unRegister(id);
    }

    @PostMapping("/updateUserInfo")
    public SimpleResponse updateUserInfo(@RequestBody UpdateUserRequest request) {
        return userService.updateInfo(request);
    }
}
