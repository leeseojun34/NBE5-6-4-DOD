package com.grepp.spring.social_auth_tokens.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SocialAuthTokensDTO {

    private Long socialAuthTokensId;

    @NotNull
    @Size(max = 255)
    @SocialAuthTokensAccessTokenUnique
    private String accessToken;

    @NotNull
    @Size(max = 255)
    @SocialAuthTokensRefreshTokenUnique
    private String refreshToken;

    @NotNull
    @Size(max = 255)
    private String tokenType;

    @NotNull
    private LocalDateTime expiresAt;

    @NotNull
    @Size(max = 255)
    private String provider;

    @Size(max = 255)
    private String user;

}
