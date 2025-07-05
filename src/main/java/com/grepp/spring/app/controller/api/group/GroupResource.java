package com.grepp.spring.app.controller.api.group;


import com.grepp.spring.app.controller.api.group.groupDto.groupRole.GroupRole;
import com.grepp.spring.app.controller.api.group.groupDto.groupSchedule.GroupSchedule;
import com.grepp.spring.app.controller.api.group.groupDto.groupUser.GroupUser;
import com.grepp.spring.app.controller.api.group.payload.ControlGroupRoleRequest;
import com.grepp.spring.app.controller.api.group.payload.ControlGroupRoleResponse;
import com.grepp.spring.app.controller.api.group.payload.CreateGroupRequest;
import com.grepp.spring.app.controller.api.group.payload.CreateGroupResponse;
import com.grepp.spring.app.controller.api.group.payload.DeleteGroupRequest;
import com.grepp.spring.app.controller.api.group.payload.DeleteGroupResponse;
import com.grepp.spring.app.controller.api.group.payload.DeportGroupMemberResponse;
import com.grepp.spring.app.controller.api.group.payload.InviteGroupMemberRequest;
import com.grepp.spring.app.controller.api.group.payload.InviteGroupMemberResponse;
import com.grepp.spring.app.controller.api.group.payload.ModifyGroupInfoRequest;
import com.grepp.spring.app.controller.api.group.payload.ModifyGroupInfoResponse;
import com.grepp.spring.app.controller.api.group.payload.ScheduleToGroupRequest;
import com.grepp.spring.app.controller.api.group.payload.ScheduleToGroupResponse;
import com.grepp.spring.app.controller.api.group.payload.ShowGroupMemberResponse;
import com.grepp.spring.app.controller.api.group.payload.ShowGroupResponse;
import com.grepp.spring.app.controller.api.group.payload.ShowGroupScheduleResponse;
import com.grepp.spring.app.controller.api.group.payload.ShowGroupStatisticsResponse;
import com.grepp.spring.app.controller.api.group.payload.WithdrawGroupResponse;
import com.grepp.spring.infra.response.ApiResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/groups",  produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupResource {

    // 현재 유저가 속한 그룹 조회
    @GetMapping
    public ResponseEntity<ApiResponse<ShowGroupResponse>> getGroup(
    ){
        // Mock Data
        ShowGroupResponse response = ShowGroupResponse.builder()
            .groupIds(new ArrayList<>(List.of(
                12342L,
                43542L,
                123412L,
                343434L
            )))
                .build();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 그룹 생성
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CreateGroupResponse>> createGroup(
        @RequestBody CreateGroupRequest request
    ){
        // Mock Data
        // message 밖에 없다.
        // message: "그룹이 생성되었습니다."
        CreateGroupResponse response = CreateGroupResponse.builder()
            .build();
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    // 그룹 내 일정 조회
    @GetMapping("/schedule-groups/{id}")
    public ResponseEntity<ApiResponse<ShowGroupScheduleResponse>> getGroupSchedules(
        @RequestParam Long id
    ){
        //Mock Data
        ShowGroupScheduleResponse response = ShowGroupScheduleResponse.builder()
            .scheduleIds(new ArrayList<>(List.of(
                343432L,
                34353243L,
                424541235L
            )))
            .build();
        return ResponseEntity.ok(ApiResponse.success(response));
        // TODO : 예외처리
        // 현재 유저가 해당 그룹의 그룹원이 아니면 403_NOT_GROUP_MEMBER
        // id가 db에 없다면 404_GROUP_NOT_FOUND
    }

    // 그룹 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DeleteGroupResponse>> deleteGroup(
        @RequestParam Long id,
        @RequestBody DeleteGroupRequest request
    ){
        // Mock Data
        DeleteGroupResponse response = DeleteGroupResponse.builder()
            .build();
        return ResponseEntity.ok(ApiResponse.noContent());
        // TODO : 예외처리
        // 현재 유저가 해당 그룹의 그룹원이 아니면 403_NOT_GROUP_MEMBER
        // 현재 유저가 해당 그룹의 그룹장이 아니면 403_NOT_GROUP_OWNER
        // id가 db에 없다면 404_GROUP_NOT_FOUND
    }

    // 그룹 멤버 조회
    @GetMapping("/{id}/member")
    public ResponseEntity<ApiResponse<ShowGroupMemberResponse>> getGroupMembers(
        @RequestParam Long id
    ){
        // MockData
        ShowGroupMemberResponse response = ShowGroupMemberResponse.builder()
            .userIds(new ArrayList<>(List.of(
                1234L,
                3434L,
                123552L,
                453234L
            )))
            .build();
        return ResponseEntity.ok(ApiResponse.success(response));
        // TODO : 예외처리
        // 현재 유저가 해당 그룹의 그룹원이 아니면 403_NOT_GROUP_MEMBER
        // id가 db에 없다면 404_GROUP_NOT_FOUND
    }

    // 그룹 정보 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ModifyGroupInfoResponse>> updateGroupInfo(
        @RequestParam Long id,
        @RequestBody ModifyGroupInfoRequest request
    ){
        // MockData
        ModifyGroupInfoResponse response = ModifyGroupInfoResponse.builder()
            .build();
        return ResponseEntity.ok(ApiResponse.noContent());
        // TODO : 예외처리
        // 현재 유저가 해당 그룹의 그룹원이 아니면 403_NOT_GROUP_MEMBER
        // 현재 유저가 해당 그룹의 그룹장이 아니면 403_NOT_GROUP_OWNER
        // id가 db에 없다면 404_GROUP_NOT_FOUND

    }

    // 그룹 멤버 내보내기
    @PatchMapping("/{groupId}/members/{userId}")
    public ResponseEntity<ApiResponse<DeportGroupMemberResponse>> deportGroupMember(
        @RequestParam Long groupId,
        @RequestParam Long userId
        ){
        // MockData
        DeportGroupMemberResponse response = DeportGroupMemberResponse.builder()
            .build();
        return ResponseEntity.ok(ApiResponse.noContent());
        // TODO : 예외처리
        // 현재 유저가 해당 그룹의 그룹원이 아니면 403_NOT_GROUP_MEMBER
        // 현재 유저가 해당 그룹의 그룹장이 아니면 403_NOT_GROUP_OWNER
        // groupId가 db에 없다면 404_GROUP_NOT_FOUND
        // userId가 db에 없다면 404_USER_NOT_FOUND
        // userId가 해당 그룹에 없다면 404_USER_NOT_IN_GROUP
    }

    // 그룹 멤버 초대
    @PostMapping("/{id}/members")
    public ResponseEntity<ApiResponse<InviteGroupMemberResponse>> inviteGroupMember(
        @RequestParam Long id,
        @RequestBody InviteGroupMemberRequest request
        ){
        // MockData
        InviteGroupMemberResponse response = InviteGroupMemberResponse.builder()
            .build();
        return ResponseEntity.ok(ApiResponse.noContent());
        // TODO : 예외처리
        // 현재 유저가 해당 그룹의 그룹원이 아니면 403_NOT_GROUP_MEMBER
        // 현재 유저가 해당 그룹의 그룹장이 아니면 403_NOT_GROUP_OWNER
        // id가 db에 없다면 404_GROUP_NOT_FOUND
        // request의 userIds중 user들이 db에 없다면 404_USER_NOT_FOUND
        // request의 userIds중 user들이 해당 그룹에 있다면 409_USER_ALREADY_IN_GROUP
    }

    // 그룹 멤버 권한 관리
    @PatchMapping("/{id}/members")
    public ResponseEntity<ApiResponse<ControlGroupRoleResponse>> controlGroupRoles(
        @RequestParam Long id,
        @RequestBody ControlGroupRoleRequest request
        ){
        // MockData
        ControlGroupRoleResponse response = ControlGroupRoleResponse.builder()
            .build();
        return ResponseEntity.ok(ApiResponse.noContent());
        // TODO : 예외처리
        // 현재 유저가 해당 그룹의 그룹원이 아니면 403_NOT_GROUP_MEMBER
        // 현재 유저가 해당 그룹의 그룹장이 아니면 403_NOT_GROUP_OWNER
        // id가 db에 없다면 404_GROUP_NOT_FOUND
        // request의 userIds중 user들이 db에 없다면 404_USER_NOT_FOUND
        // request의 userIds중 user들이 해당 그룹에 없다면 404_USER_NOT_IN_GROUP
    }

    // 그룹 통계 조회
    @GetMapping("/{id}/statistics")
    public ResponseEntity<ApiResponse<ShowGroupStatisticsResponse>> getGroupStatistics(
        @RequestParam Long id
        ){
        // MockData
        ShowGroupStatisticsResponse response = ShowGroupStatisticsResponse.builder()
            .groupUsers(new ArrayList<>(List.of(
                new GroupUser(1234L, GroupRole.GROUP_LEADER, new ArrayList<>(List.of(
                    34342L,
                    34444L,
                    55345L
                ))),
                new GroupUser(1236L, GroupRole.GROUP_MEMBER, new ArrayList<>(List.of(
                    34342L,
                    55345L
                ))),
                new GroupUser(1239L, GroupRole.GROUP_MEMBER, new ArrayList<>(List.of(
                    34444L,
                    55345L
                )))
            )))
            .groupSchedules(new ArrayList<>(List.of(
                new GroupSchedule(34342L, "성수역", LocalDateTime.now(), LocalDateTime.MAX),
                new GroupSchedule(34444L, "용산역", LocalDateTime.now(), LocalDateTime.MAX),
                new GroupSchedule(55345L, "합정역", LocalDateTime.now(), LocalDateTime.MAX)
                )))
            .build();
        return ResponseEntity.ok(ApiResponse.success(response));
        // TODO : 예외처리
        // 현재 유저가 해당 그룹의 그룹원이 아니면 403_NOT_GROUP_MEMBER
        // id가 db에 없다면 404_GROUP_NOT_FOUND
    }

    // 일정 -> 그룹 일정으로 이동
    @PatchMapping("/move-schedule")
    public ResponseEntity<ApiResponse<ScheduleToGroupResponse>> moveScheduleToGroup(
        @RequestBody ScheduleToGroupRequest request
        ){
        // MockData
        ScheduleToGroupResponse response = ScheduleToGroupResponse.builder()
            .build();
        return ResponseEntity.ok(ApiResponse.noContent());
        // TODO : 예외처리
        // 현재 유저가 해당 일정의 구성원이 아니면 403_NOT_SCHEDULE_MEMBER
        // 현재 유저가 해당 일정의 팀장이 아니면 403_NOT_SCHEDULE_OWNER
        // request의 scheduleId가 db에 없다면 404_SCHEDULE_NOT_FOUND
        // request의 scheduleId가 이미 그룹에 있다면 409_SCHEDULE_ALREADY_IN_GROUP
    }

    // 그룹 탈퇴
    @PatchMapping("/{id}/leave")
    public ResponseEntity<ApiResponse<WithdrawGroupResponse>> withdrawGroup(
        @RequestParam Long id
        ){
        // MockData
        WithdrawGroupResponse response = WithdrawGroupResponse.builder()
            .build();
        return ResponseEntity.ok(ApiResponse.noContent());
        // TODO : 예외처리
        // 현재 유저가 해당 일정의 구성원이 아니면 403_NOT_SCHEDULE_MEMBER
        // id가 db에 없다면 404_GROUP_NOT_FOUND
    }

}