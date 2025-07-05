package com.grepp.spring.app.controller.api.group.groupDto.groupUser;

import com.grepp.spring.app.controller.api.group.groupDto.groupRole.GroupRole;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GroupUser {
    private Long userId;
    private GroupRole groupRole;
    private ArrayList<Long> scheduleIds;

}
