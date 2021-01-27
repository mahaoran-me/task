package com.task.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String email;
}
