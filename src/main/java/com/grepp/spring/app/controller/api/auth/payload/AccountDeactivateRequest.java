package com.grepp.spring.app.controller.api.auth.payload;

import lombok.Data;

@Data
public class AccountDeactivateRequest {

    private String password;
    private String groupRole;

}
