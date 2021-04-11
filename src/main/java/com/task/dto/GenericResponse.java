package com.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class GenericResponse<T> extends SimpleResponse {
    private T data;

    public GenericResponse(int i, String message, T data) {
        super(i, message);
        this.data = data;
    }
}
