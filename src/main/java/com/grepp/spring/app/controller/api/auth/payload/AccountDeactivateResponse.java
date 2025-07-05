package com.grepp.spring.app.controller.api.auth.payload;

import lombok.Data;

@Data
public class AccountDeactivateResponse {

    private String id;
    private String role;
    private String name;

}
