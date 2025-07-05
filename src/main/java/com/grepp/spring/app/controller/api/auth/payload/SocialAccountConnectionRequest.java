package com.grepp.spring.app.controller.api.auth.payload;

import lombok.Data;

@Data
public class SocialAccountConnectionRequest {

    private String provider;
    private String providerId;
    private String authorizationCode;
    private String accessToken;
    private String refreshToken;


}
