package com.grepp.spring.app.controller.api.auth.payload;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
