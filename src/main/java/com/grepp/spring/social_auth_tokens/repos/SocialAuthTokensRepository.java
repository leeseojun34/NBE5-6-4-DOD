package com.grepp.spring.social_auth_tokens.repos;

import com.grepp.spring.member.domain.Member;
import com.grepp.spring.social_auth_tokens.domain.SocialAuthTokens;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SocialAuthTokensRepository extends JpaRepository<SocialAuthTokens, Long> {

    SocialAuthTokens findFirstByUser(Member member);

    boolean existsByAccessTokenIgnoreCase(String accessToken);

    boolean existsByRefreshTokenIgnoreCase(String refreshToken);

}
