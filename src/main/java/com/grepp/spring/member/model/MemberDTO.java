package com.grepp.spring.member.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MemberDTO {

    @Size(max = 255)
    @MemberUserIdValid
    private String userId;

    @NotNull
    @Size(max = 255)
    private String password;

    @NotNull
    @Size(max = 255)
    private String provider;

    @NotNull
    @Size(max = 255)
    private String role;

    @NotNull
    @Size(max = 255)
    @MemberEmailUnique
    private String email;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private Long profileImageNumber;

    @NotNull
    @Size(max = 255)
    @MemberPhoneNumberUnique
    private String phoneNumber;

}
