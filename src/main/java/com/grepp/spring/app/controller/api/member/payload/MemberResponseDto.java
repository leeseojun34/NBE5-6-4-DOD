package com.grepp.spring.app.controller.api.member.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberResponseDto {

    private String id;
    private String email;
    private String name;
    private Long profileImageNumber;
    private String provider;
    private String role;


}
