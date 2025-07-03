package com.grepp.spring.group_user.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GroupUserDTO {

    private Long groupUserId;

    @NotNull
    @Size(max = 255)
    private String groupRole;

    @Size(max = 255)
    private String user;

    private Long group;

}
