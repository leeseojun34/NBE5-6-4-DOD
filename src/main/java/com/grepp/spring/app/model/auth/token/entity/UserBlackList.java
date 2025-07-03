package com.grepp.spring.app.model.auth.token.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.OffsetDateTime;

@Getter
@Setter
@RedisHash("userBlackList")
public class UserBlackList {
    
    @Id
    private String email;
    private OffsetDateTime createdAt = OffsetDateTime.now();
    
    public UserBlackList(String email) {
        this.email = email;
    }
}
