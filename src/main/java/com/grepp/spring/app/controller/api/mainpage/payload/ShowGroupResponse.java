package com.grepp.spring.app.controller.api.mainpage.payload;

import com.grepp.spring.app.model.group_member.domain.GroupMember;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class ShowGroupResponse {



  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class GroupInfo {
    private Long groupId;
    private String groupName;
    private String description;
    private int memberCount;
    private LocalDateTime createdAt;
    private String createdBy;
    private List<GroupMember> members;
  }

}
