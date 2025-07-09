package com.grepp.spring.app.model.auth;

import com.grepp.spring.app.controller.api.auth.payload.LoginRequest;
import com.grepp.spring.app.model.auth.dto.TokenDto;
import com.grepp.spring.app.model.auth.token.RefreshTokenService;
import com.grepp.spring.app.model.auth.token.entity.RefreshToken;
import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.member.domain.Role;
import com.grepp.spring.app.model.member.repos.MemberRepository;
import com.grepp.spring.infra.auth.jwt.JwtTokenProvider;
import com.grepp.spring.infra.auth.jwt.dto.AccessTokenDto;
import com.grepp.spring.infra.auth.oauth2.user.OAuth2UserInfo;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final MemberRepository memberRepository;

//    public TokenDto signin(LoginRequest loginRequest) {
//        UsernamePasswordAuthenticationToken authenticationToken =
//            new UsernamePasswordAuthenticationToken(loginRequest.getId(),
//                loginRequest.getPassword());
//
//        // loadUserByUsername + password 검증 후 인증 객체 반환
//        // 인증 실패 시: AuthenticationException 발생
//        Authentication authentication = authenticationManagerBuilder.getObject()
//            .authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String roles =  String.join(",", authentication.getAuthorities().stream().map(
//            GrantedAuthority::getAuthority).toList());
//        return processTokenSignin(authentication.getName());
//    }


    @Transactional
    public TokenDto processTokenSignin(OAuth2UserInfo userInfo) {

        // id는 provider 와 providerId 를 합쳐서 생성
        String userId = userInfo.getProvider() + "_" + userInfo.getProviderId();

        Optional<Member> existMember = memberRepository.findById(userId);
        Member member;

        // 유저가 기가입 회원인지 체크
        if (existMember.isPresent()) {
            member = existMember.get();
        } else {
            member = new Member();
            member.setId(userId);
            member.setProvider(userInfo.getProvider());
            member.setName(userInfo.getName());
            member.setEmail(userInfo.getEmail());
            member.setRole(Role.ROLE_USER);
            member.setProfileImageNumber((long) new Random().nextInt(10));
            member.setPassword("123qwe!@#");
            // 일단 전화번호는 나중에 받고
            // 카카오는 이메일 안되는디 어떡하지 일단 구글만 해보고
            // 프로필 사진은 모르겠다 일단 아무 숫자나 넣자
            memberRepository.save(member);
            log.info("새로운 유저 DB에 저장 완료: {}", userId);
        }

        AccessTokenDto accessToken = jwtTokenProvider.generateAccessToken(userId, Role.ROLE_USER.name());
        RefreshToken refreshToken = refreshTokenService.saveWithAtId(accessToken.getJti());

        return TokenDto.builder()
            .accessToken(accessToken.getToken())
            .expiresIn(accessToken.getExpires()) // 만료기간 엑세스 토큰만 해도 되는지 ?
            .refreshToken(refreshToken.getToken())
            .userId(userId)
            .userName(userInfo.getName())
            .build();
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
//        AccessTokenDto accessToken = jwtTokenProvider.generateAccessToken(userId, roles);
//        RefreshToken refreshToken = refreshTokenService.saveWithAtId(accessToken.getJti());
//
//        Member member = memberRepository.findById(userId)
//            .orElseThrow(() -> new BadCredentialsException("인증된 사용자를 찾을 수 없습니다."));
//
//        return TokenDto.builder()
//            .accessToken(accessToken.getToken())
//            .atId(accessToken.getJti())
//            .grantType("Bearer")
//            .refreshToken(refreshToken.getToken())
//            .refreshExpiresIn(jwtTokenProvider.getRefreshTokenExpiration())
//            .expiresIn(jwtTokenProvider.getAccessTokenExpiration())
//            .userId(userId)
//            .userName(member.getName())
//            .build();
    }

//    @Transactional
//    public void logout(String userId, String accessTokenJti){
//        log.info("로그아웃 요청: user ID: {}, access Token JTI: {}", userId, accessTokenJti);
//
//        // 세션에 있는 쿠키 날리기
//        refreshTokenService.deleteByAccessTokenId(accessTokenJti);
//        log.info("refresh Token 삭제 완료");
//
//        // 소셜 제공자의 토큰 무효화 요청
//        Member member = memberRepository.findById(userId)
//            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
//
//        socialLogoutService.revokeSocialToken(member.getProvider(), member.getSocial)
//
//    }

}
