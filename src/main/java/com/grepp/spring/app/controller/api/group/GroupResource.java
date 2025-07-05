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
import com.grepp.spring.infra.error.exceptions.AuthApiException;
import com.grepp.spring.infra.response.ApiResponse;
import com.grepp.spring.infra.response.ResponseCode;
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
@RequestMapping(value = "/api/v1/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupResource {

    // 현재 유저가 속한 그룹 조회
    @GetMapping
    public ResponseEntity<ApiResponse<ShowGroupResponse>> getGroup(
    ) {
        try {
            // Mock Data
            ShowGroupResponse response = ShowGroupResponse.builder()
                .groupIds(new ArrayList<>(List.of(
                    12340L, 56780L, 98765L,
                    123456L, 987654L, 999999L
                )))
                .build();
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
    }


    // 그룹 생성
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CreateGroupResponse>> createGroup(
        @RequestBody CreateGroupRequest request
    ) {
        try {
            // Mock Data
            CreateGroupResponse response = CreateGroupResponse.builder()
                .build();
            return ResponseEntity.ok(ApiResponse.success("그룹이 생성되었습니다."));
        } catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
    }


    // 그룹 내 일정 조회
    @GetMapping("/schedule-groups/{id}")
    public ResponseEntity<ApiResponse<ShowGroupScheduleResponse>> getGroupSchedules(
        @RequestParam Long id
    ) {
        try {
            // 예외 발생
            // id가 db에 없다면 404_GROUP_NOT_FOUND
            if(
                id!=12340L && id!=56780L && id!=98765L &&
                id!=123456L && id!=987654L && id!=999999L
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 그룹을 찾을 수 없습니다."));
            }

            //Mock Data
            ShowGroupScheduleResponse response = ShowGroupScheduleResponse.builder()
                .scheduleIds(new ArrayList<>(List.of(
                    12121L, 34343L, 56565L,
                    78787L, 90909L
                )))
                .build();
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
        // TODO : 예외처리
        // 현재 유저가 해당 그룹의 그룹원이 아니면 403_NOT_GROUP_MEMBER
    }


    // 그룹 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DeleteGroupResponse>> deleteGroup(
        @RequestParam Long id,
        @RequestBody DeleteGroupRequest request
    ) {
        try {
            // 예외 발생
            // id가 db에 없다면 404_GROUP_NOT_FOUND
            if(
                id!=12340L && id!=56780L && id!=98765L &&
                    id!=123456L && id!=987654L && id!=999999L
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 그룹을 찾을 수 없습니다."));
            }

            // Mock Data
            DeleteGroupResponse response = DeleteGroupResponse.builder()
                .build();
            return ResponseEntity.ok(ApiResponse.success("그룹이 삭제되었습니다."));
        } catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
        // TODO : 예외처리
        // 현재 유저가 해당 그룹의 그룹원이 아니면 403_NOT_GROUP_MEMBER
        // 현재 유저가 해당 그룹의 그룹장이 아니면 403_NOT_GROUP_OWNER
    }


    // 그룹 멤버 조회
    @GetMapping("/{id}/member")
    public ResponseEntity<ApiResponse<ShowGroupMemberResponse>> getGroupMembers(
        @RequestParam Long id
    ) {
        try {
            // 예외 발생
            // id가 db에 없다면 404_GROUP_NOT_FOUND
            if(
                id!=12340L && id!=56780L && id!=98765L &&
                    id!=123456L && id!=987654L && id!=999999L
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 그룹을 찾을 수 없습니다."));
            }

            // MockData
            ShowGroupMemberResponse response = ShowGroupMemberResponse.builder()
                .userIds(new ArrayList<>(List.of(
                    1234L, 1235L, 2345L, 2346L, 3456L,
                    3457L, 4567L, 4568L, 5678L, 5679L
                    //6789L, 6790L, 7890L, 7891L,
                    //8901L, 8902L, 9012L, 9013L
                )))
                .build();
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
        // TODO : 예외처리
        // 현재 유저가 해당 그룹의 그룹원이 아니면 403_NOT_GROUP_MEMBER
    }


    // 그룹 정보 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ModifyGroupInfoResponse>> updateGroupInfo(
        @RequestParam Long id,
        @RequestBody ModifyGroupInfoRequest request
    ) {
        try {
            // 예외 발생
            // id가 db에 없다면 404_GROUP_NOT_FOUND
            if(
                id!=12340L && id!=56780L && id!=98765L &&
                    id!=123456L && id!=987654L && id!=999999L
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 그룹을 찾을 수 없습니다."));
            }

            // MockData
            ModifyGroupInfoResponse response = ModifyGroupInfoResponse.builder()
                .build();
            return ResponseEntity.ok(ApiResponse.success("그룹 내용이 수정되었습니다."));
        } catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
        // TODO : 예외처리
        // 현재 유저가 해당 그룹의 그룹원이 아니면 403_NOT_GROUP_MEMBER
        // 현재 유저가 해당 그룹의 그룹장이 아니면 403_NOT_GROUP_OWNER
    }


    // 그룹 멤버 내보내기
    @PatchMapping("/{groupId}/members/{userId}")
    public ResponseEntity<ApiResponse<DeportGroupMemberResponse>> deportGroupMember(
        @RequestParam Long groupId,
        @RequestParam Long userId
    ) {
        try {
            // 예외 발생
            // groupId가 db에 없다면 404_GROUP_NOT_FOUND
            if(
                groupId!=12340L && groupId!=56780L && groupId!=98765L &&
                    groupId!=123456L && groupId!=987654L && groupId!=999999L
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 그룹을 찾을 수 없습니다."));
            }
            // userId가 db에 없다면 404_USER_NOT_FOUND
            if(
                userId!=1234L && userId!=1235L && userId!=2345L && userId!=2346L && userId!=3456L &&
                    userId!=3457L && userId!=4567L && userId!=4568L && userId!=5678L && userId!=5679L &&
                    userId!=6789L && userId!=6790L && userId!=7890L && userId!=7891L &&
                    userId!=8901L && userId!=8902L && userId!=9012L && userId!=9013L
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 유저를 찾을 수 없습니다."));
            }
            // userId가 해당 그룹에 없다면 404_USER_NOT_IN_GROUP
            if(
                    userId!=1234L && userId!=1235L && userId!=2345L && userId!=2346L && userId!=3456L &&
                    userId!=3457L && userId!=4567L && userId!=4568L && userId!=5678L && userId!=5679L
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 유저를 그룹에서 찾을 수 없습니다."));
            }

            // MockData
            DeportGroupMemberResponse response = DeportGroupMemberResponse.builder()
                .build();
            return ResponseEntity.ok(ApiResponse.success("그룹에서 해당 유저를 내보냈습니다."));
        } catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
        // TODO : 예외처리
        // 현재 유저가 해당 그룹의 그룹원이 아니면 403_NOT_GROUP_MEMBER
        // 현재 유저가 해당 그룹의 그룹장이 아니면 403_NOT_GROUP_OWNER
    }


    // 그룹 멤버 초대
    @PostMapping("/{id}/members")
    public ResponseEntity<ApiResponse<InviteGroupMemberResponse>> inviteGroupMember(
        @RequestParam Long id,
        @RequestBody InviteGroupMemberRequest request
    ) {
        try {
            // 예외 발생
            // groupId가 db에 없다면 404_GROUP_NOT_FOUND
            if(
                id!=12340L && id!=56780L && id!=98765L &&
                    id!=123456L && id!=987654L && id!=999999L
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 그룹을 찾을 수 없습니다."));
            }

            for(Long tempUser: request.getUserIds()){
                // request의 userIds중 user들이 해당 그룹에 있다면 409_USER_ALREADY_IN_GROUP
                if(
                    tempUser == 1234L || tempUser==1235L || tempUser==2345L || tempUser==2346L || tempUser==3456L ||
                        tempUser==3457L || tempUser==4567L || tempUser==4568L || tempUser==5678L || tempUser==5679L
                ){
                    return ResponseEntity.status(409)
                        .body(ApiResponse.error(ResponseCode.CONFLICT_REGISTER, "이미 해당 그룹에 존재하는 유저가 존재합니다."));
                }
                // request의 userIds중 user들이 db에 없다면 404_USER_NOT_FOUND
                else if(

                        tempUser!=6789L && tempUser!=6790L && tempUser!=7890L && tempUser!=7891L &&
                        tempUser!=8901L && tempUser!=8902L && tempUser!=9012L && tempUser!=9013L
                ){
                    return ResponseEntity.status(404)
                        .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 유저를 찾을 수 없습니다."));
                }
            }

            // MockData
            InviteGroupMemberResponse response = InviteGroupMemberResponse.builder()
                .build();
            return ResponseEntity.ok(ApiResponse.success("초대한 유저들이 구룹에 추가 되었습니다."));
        } catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
        // TODO : 예외처리
        // 현재 유저가 해당 그룹의 그룹원이 아니면 403_NOT_GROUP_MEMBER
        // 현재 유저가 해당 그룹의 그룹장이 아니면 403_NOT_GROUP_OWNER
    }


    // 그룹 멤버 권한 관리
    @PatchMapping("/{id}/members")
    public ResponseEntity<ApiResponse<ControlGroupRoleResponse>> controlGroupRoles(
        @RequestParam Long id,
        @RequestBody ControlGroupRoleRequest request
    ) {
        try {
            // 예외 발생
            // groupId가 db에 없다면 404_GROUP_NOT_FOUND
            if(
                id!=12340L && id!=56780L && id!=98765L &&
                    id!=123456L && id!=987654L && id!=999999L
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 그룹을 찾을 수 없습니다."));
            }
            // request의 userIds중 user들이 db에 없다면 404_USER_NOT_FOUND
            if(
                request.getUserId()!=1234L && request.getUserId()!=1235L && request.getUserId()!=2345L && request.getUserId()!=2346L && request.getUserId()!=3456L &&
                    request.getUserId()!=3457L && request.getUserId()!=4567L && request.getUserId()!=4568L && request.getUserId()!=5678L && request.getUserId()!=5679L &&
                    request.getUserId()!=6789L && request.getUserId()!=6790L && request.getUserId()!=7890L && request.getUserId()!=7891L &&
                    request.getUserId()!=8901L && request.getUserId()!=8902L && request.getUserId()!=9012L && request.getUserId()!=9013L
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 유저를 찾을 수 없습니다."));
            }
            // userId가 해당 그룹에 없다면 404_USER_NOT_IN_GROUP
            if(
                request.getUserId()!=1234L && request.getUserId()!=1235L && request.getUserId()!=2345L && request.getUserId()!=2346L && request.getUserId()!=3456L &&
                    request.getUserId()!=3457L && request.getUserId()!=4567L && request.getUserId()!=4568L && request.getUserId()!=5678L && request.getUserId()!=5679L
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 유저를 그룹에서 찾을 수 없습니다."));
            }

            // MockData
            ControlGroupRoleResponse response = ControlGroupRoleResponse.builder()
                .build();
            return ResponseEntity.ok(ApiResponse.success("해당 유저의 권한이 재설정 되었습니다."));
        } catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
        // TODO : 예외처리
        // 현재 유저가 해당 그룹의 그룹원이 아니면 403_NOT_GROUP_MEMBER
        // 현재 유저가 해당 그룹의 그룹장이 아니면 403_NOT_GROUP_OWNER
    }

    // 그룹 통계 조회
    @GetMapping("/{id}/statistics")
    public ResponseEntity<ApiResponse<ShowGroupStatisticsResponse>> getGroupStatistics(
        @RequestParam Long id
    ) {
        try {
            // 예외 발생
            // groupId가 db에 없다면 404_GROUP_NOT_FOUND
            if(
                id!=12340L && id!=56780L && id!=98765L &&
                    id!=123456L && id!=987654L && id!=999999L
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 그룹을 찾을 수 없습니다."));
            }

            // MockData
            ShowGroupStatisticsResponse response = ShowGroupStatisticsResponse.builder()
                .groupUsers(new ArrayList<>(List.of(
                    new GroupUser(1234L, GroupRole.GROUP_LEADER, new ArrayList<>(List.of(12121L, 34343L, 56565L, 78787L, 90909L))),
                    new GroupUser(1235L, GroupRole.GROUP_MEMBER, new ArrayList<>(List.of(12121L, 34343L, 90909L))),
                    new GroupUser(2345L, GroupRole.GROUP_MEMBER, new ArrayList<>(List.of(56565L, 78787L, 90909L))),
                    new GroupUser(2346L, GroupRole.GROUP_MEMBER, new ArrayList<>(List.of(12121L, 56565L, 78787L, 90909L))),
                    new GroupUser(3456L, GroupRole.GROUP_MEMBER, new ArrayList<>(List.of(12121L, 34343L, 56565L, 78787L, 90909L))),
                    new GroupUser(3457L, GroupRole.GROUP_MEMBER, new ArrayList<>(List.of(12121L, 90909L))),
                    new GroupUser(4567L, GroupRole.GROUP_MEMBER, new ArrayList<>(List.of(12121L, 34343L, 56565L))),
                    new GroupUser(4568L, GroupRole.GROUP_MEMBER, new ArrayList<>(List.of( 34343L, 78787L))),
                    new GroupUser(5678L, GroupRole.GROUP_MEMBER, new ArrayList<>(List.of(56565L, 78787L))),
                    new GroupUser(5679L, GroupRole.GROUP_MEMBER, new ArrayList<>(List.of(12121L, 34343L, 78787L, 90909L)))
                )))
                .groupSchedules(new ArrayList<>(List.of(
                    new GroupSchedule(12121L, "성수역", LocalDateTime.now(), LocalDateTime.MAX),
                    new GroupSchedule(34343L, "용산역", LocalDateTime.now(), LocalDateTime.MAX),
                    new GroupSchedule(56565L, "합정역", LocalDateTime.now(), LocalDateTime.MAX),
                    new GroupSchedule(78787L, "역삼역", LocalDateTime.now(), LocalDateTime.MAX),
                    new GroupSchedule(90909L, "서초역", LocalDateTime.now(), LocalDateTime.MAX)
                )))
                .build();
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
        // TODO : 예외처리
        // 현재 유저가 해당 그룹의 그룹원이 아니면 403_NOT_GROUP_MEMBER
    }


    // 일정 -> 그룹 일정으로 이동
    @PatchMapping("/move-schedule")
    public ResponseEntity<ApiResponse<ScheduleToGroupResponse>> moveScheduleToGroup(
        @RequestBody ScheduleToGroupRequest request
    ) {
        try {
            // 예외 발생
            // request의 scheduleId가 이미 그룹에 있다면 409_SCHEDULE_ALREADY_IN_GROUP
                if(
                    request.getScheduleId()==12121L || request.getScheduleId()==34343L || request.getScheduleId()==56565L ||
                        request.getScheduleId()==78787L || request.getScheduleId()==90909L || request.getScheduleId()==11111L ||
                        request.getScheduleId()==22222L || request.getScheduleId()==33333L || request.getScheduleId()==44444L
                ){
                    return ResponseEntity.status(409)
                        .body(ApiResponse.error(ResponseCode.CONFLICT_REGISTER, "이미 그룹에 존재하는 일정 입니다."));
                }
                // request의 scheduleId가 db에 없다면 404_SCHEDULE_NOT_FOUND
                else if(
                    request.getScheduleId()!=55555L && request.getScheduleId()!=66666L && request.getScheduleId()!=77777L &&
                        request.getScheduleId()!=88888L && request.getScheduleId()!=99999L
                ){
                    return ResponseEntity.status(404)
                        .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 일정을 찾을 수 없습니다."));
                }

            // MockData
            ScheduleToGroupResponse response = ScheduleToGroupResponse.builder()
                .build();
            return ResponseEntity.ok(ApiResponse.success("일회성 일정에서 그룹으로 바뀌었습니다."));
        } catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
        // TODO : 예외처리
        // 현재 유저가 해당 일정의 구성원이 아니면 403_NOT_SCHEDULE_MEMBER
        // 현재 유저가 해당 일정의 팀장이 아니면 403_NOT_SCHEDULE_OWNER
    }

    // 그룹 탈퇴
    @PatchMapping("/{id}/leave")
    public ResponseEntity<ApiResponse<WithdrawGroupResponse>> withdrawGroup(
        @RequestParam Long id
    ) {
        try {
            // 예외 발생
            // groupId가 db에 없다면 404_GROUP_NOT_FOUND
            if(
                id!=12340L && id!=56780L && id!=98765L &&
                    id!=123456L && id!=987654L && id!=999999L
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 그룹을 찾을 수 없습니다."));
            }

            // MockData
            WithdrawGroupResponse response = WithdrawGroupResponse.builder()
                .build();
            return ResponseEntity.ok(ApiResponse.success("그룹에서 탈퇴하였습니다."));
        } catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
        // TODO : 예외처리
        // 현재 유저가 해당 일정의 구성원이 아니면 403_NOT_SCHEDULE_MEMBER
    }

}