package com.grepp.spring.app.controller.api.group.payload;

import com.grepp.spring.app.controller.api.group.groupDto.groupRole.GroupRole;
import lombok.Getter;

@Getter
public class ControlGroupRoleRequest {
    private Long userId;
    private GroupRole groupRole;

}
