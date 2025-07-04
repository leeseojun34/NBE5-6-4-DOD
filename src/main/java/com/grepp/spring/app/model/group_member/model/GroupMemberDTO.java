package com.grepp.spring.app.model.group_member.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GroupMemberDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String role;

    @Size(max = 255)
    private String member;

    private Long group;

}
