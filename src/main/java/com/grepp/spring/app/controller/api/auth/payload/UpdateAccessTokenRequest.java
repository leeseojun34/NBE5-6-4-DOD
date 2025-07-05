package com.grepp.spring.app.controller.api.auth.payload;

import lombok.Data;

@Data
public class UpdateAccessTokenRequest {

    private String refreshToken;

}
