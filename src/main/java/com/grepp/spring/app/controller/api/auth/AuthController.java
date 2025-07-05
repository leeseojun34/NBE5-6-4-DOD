package com.grepp.spring.app.controller.api.auth;

import com.grepp.spring.app.controller.api.auth.payload.AccountDeactivateRequest;
import com.grepp.spring.app.controller.api.auth.payload.AccountDeactivateResponse;
import com.grepp.spring.app.controller.api.auth.payload.LoginRequest;
import com.grepp.spring.app.controller.api.auth.payload.LoginResponse;
import com.grepp.spring.app.controller.api.auth.payload.LogoutRequest;
import com.grepp.spring.app.controller.api.auth.payload.LogoutResponse;
import com.grepp.spring.app.controller.api.auth.payload.RegisterRequest;
import com.grepp.spring.app.controller.api.auth.payload.RegisterResponse;
import com.grepp.spring.app.controller.api.auth.payload.SocialAccountConnectionRequest;
import com.grepp.spring.app.controller.api.auth.payload.SocialAccountResponse;
import com.grepp.spring.app.controller.api.auth.payload.UpdateAccessTokenRequest;
import com.grepp.spring.infra.response.ApiResponse;
import com.grepp.spring.infra.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Operation(summary = "회원 가입", description = "소셜 계정으로 회원가입 하는 사용자들에 대한 검증을 진행합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "회원 가입 완료",
    content = @Content(mediaType = "application/json",
    schema = @Schema(implementation = RegisterResponse.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "이미 가입된 회원입니다.",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = RegisterResponse.class)))
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request) {
        if (!request.getId().equals("GOOGLE01827591823")) {
            return ResponseEntity.status(201)
                .body(ApiResponse.successCreated(
                    new RegisterResponse("GOOGLE01827591823", "ROLE_USER", "하명도")));
        } else {
            return ResponseEntity.status(409)
                .body(ApiResponse.conflictRegister(
                    new RegisterResponse("GOOGLE01827591823", "ROLE_USER", "하명도")));
        }
    }

    @Operation(summary = "로그인", description = "소셜 계정으로 로그인을 진행합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2000", description = "로그인 완료.",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = LoginResponse.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2001", description = "계정이 재활성화 되었습니다.",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = LoginResponse.class)))

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {

        if (request.getActivated()){
            return ResponseEntity.ok(ApiResponse.noContent());
        } else {
            return ResponseEntity.ok(ApiResponse.success(
                new LoginResponse("GOOGLE01827591823", "ROLE_USER", "하명도",
                    "eyJhbGciOiJIUzI1NiIsInR5cCI6Ik", "hioKcQ921Nsjns6h2LLAschbnauwd")));
        }
    }

    @Operation(summary = "회원 탈퇴", description = "서비스 탈퇴를 진행합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2000", description = "탈퇴에 성공한 경우",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = AccountDeactivateResponse.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "4010", description = "Token이 유효하지 않은 경우",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = AccountDeactivateResponse.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "4030", description = "그룹의 관리자인 경우",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = AccountDeactivateResponse.class)))

    @PatchMapping("/deactivate")
    public ResponseEntity<ApiResponse<?>> deactivate(@Valid @RequestBody AccountDeactivateRequest request,
        String accessToken) {
        if (request.getGroupRole().equals("ROLE_ADMIN")){
            return ResponseEntity.status(403)
                .body(ApiResponse.error(ResponseCode.ADMIN_WITHDRAWAL_NOT_ALLOWED,
                    Map.of("groupId", "10001", "groupName", "대나무행주")));
        } else if (!request.getPassword().equals("123qwe!@#")) {
            return ResponseEntity.status(401)
                .body(ApiResponse.error(ResponseCode.BAD_CREDENTIAL, null));
        } else if (accessToken.equals("jsn19fgjsnaljsoqon923")) {
            return ResponseEntity.ok(ApiResponse.noContent());
        } else {
            return ResponseEntity.status(401)
                .body(ApiResponse.error(ResponseCode.INVALID_TOKEN, null));
        }
    }

    @Operation(summary = "로그아웃", description = "로그아웃을 진행합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2040", description = "로그아웃에 성공한 경우",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = LogoutResponse.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "4010", description = "Token이 유효하지 않은 경우",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = LogoutResponse.class)))

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logout(String accessToken) {
        if (accessToken.equals("jsn19fgjsnaljsoqon923")) {
            return ResponseEntity.ok(ApiResponse.noContent());
        }else {
            return ResponseEntity.status(401)
                .body(ApiResponse.error(ResponseCode.INVALID_TOKEN, null));
        }
    }

    @Operation(summary = "소셜 연동 조회", description = "계정에 연동된 소셜 계정들을 조회합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2001", description = "소셜 계정 연동 정보를 성공적으로 조회한 경우",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = SocialAccountResponse.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "4010", description = "Token이 유효하지 않은 경우",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = SocialAccountResponse.class)))
    @GetMapping("/social-connections")
    public ResponseEntity<ApiResponse<?>> socialAccount (String accessToken) {
        if (accessToken.equals("jsn19fgjsnaljsoqon923")) {
            return ResponseEntity.ok(ApiResponse.success(Map.of("socialAuthTokenId","jgnsjn198283718", "provider", "GOOGLE")));
        } else {
            return ResponseEntity.status(401)
                .body(ApiResponse.error(ResponseCode.INVALID_TOKEN, null));
        }
    }

    @Operation(summary = "소셜 연동 요청", description = "계정에 새로운 소셜 계정을 연동합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2001", description = "소셜 계정 연동 정보를 성공적으로 조회한 경우",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = SocialAccountResponse.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "4010", description = "Token이 유효하지 않은 경우",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = SocialAccountResponse.class)))
    @PostMapping("/social-connections")
    public ResponseEntity<ApiResponse<?>> socialAccountConnections(@Valid @RequestBody SocialAccountConnectionRequest request
    , String accessToken) {
        if (!accessToken.equals("jsn19fgjsnaljsoqon923")) {
            return ResponseEntity.status(401)
                .body(ApiResponse.error(ResponseCode.INVALID_TOKEN, null));
        } else if (request.getAuthorizationCode().equals("dkftndjqtsmsdlswmdzhem123")) {
            return ResponseEntity.ok(ApiResponse.noContent());
            
        } else {
            return ResponseEntity.status(404)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> updateAccessToken(@Valid @RequestBody UpdateAccessTokenRequest request) {
        if (request.getRefreshToken().equals("hioKcQ921Nsjns6h2LLAschbnauwd")){
            return ResponseEntity.ok(ApiResponse.success(
                Map.of("accessToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6Ik",
                    "refreshToken", "hioKcQ921Nsjns6h2LLAschbnauwd")));
        } else {
            return ResponseEntity.status(401)
                .body(ApiResponse.error(ResponseCode.INVALID_TOKEN));
        }
    }


}
