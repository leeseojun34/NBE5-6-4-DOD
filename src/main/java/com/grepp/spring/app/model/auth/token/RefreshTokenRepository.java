package com.grepp.spring.app.model.auth.token;

import com.grepp.spring.app.model.auth.token.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByAtId(String atId);
    void deleteByAtId(String atId);
}
