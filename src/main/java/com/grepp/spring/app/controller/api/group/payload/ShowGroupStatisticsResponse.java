package com.grepp.spring.app.controller.api.group.payload;

import com.grepp.spring.app.controller.api.group.groupDto.groupSchedule.GroupSchedule;
import com.grepp.spring.app.controller.api.group.groupDto.groupUser.GroupUser;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ShowGroupStatisticsResponse {
    private ArrayList<GroupUser> groupUsers;
    private ArrayList<GroupSchedule> groupSchedules;

}
