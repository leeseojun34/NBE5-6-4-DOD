package com.grepp.spring.infra.response;

import org.springframework.http.HttpStatus;

public enum ResponseCode {
    OK("0000", HttpStatus.OK, "정상적으로 완료되었습니다."),
    BAD_REQUEST("4000", HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_FILENAME("4001", HttpStatus.BAD_REQUEST, "사용 할 수 없는 파일 이름입니다."),
    UNAUTHORIZED("4010", HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    BAD_CREDENTIAL("4011", HttpStatus.UNAUTHORIZED, "아이디나 비밀번호가 틀렸습니다."),
    NOT_FOUND("4040", HttpStatus.NOT_FOUND, "NOT FOUND"),
    NOT_EXIST_PRE_AUTH_CREDENTIAL("4012", HttpStatus.OK, "사전 인증 정보가 요청에서 발견되지 않았습니다."),
    INTERNAL_SERVER_ERROR("5000", HttpStatus.INTERNAL_SERVER_ERROR, "서버에러 입니다."),
    SECURITY_INCIDENT("6000", HttpStatus.OK, "비정상적인 로그인 시도가 감지되었습니다.");
    
    private final String code;
    private final HttpStatus status;
    private final String message;
    
    ResponseCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
    
    public String code() {
        return code;
    }
    
    public HttpStatus status() {
        return status;
    }
    
    public String message() {
        return message;
    }
}
