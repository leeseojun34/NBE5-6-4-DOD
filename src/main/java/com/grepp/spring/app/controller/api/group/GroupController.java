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
import io.swagger.v3.oas.annotations.Operation;
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
public class GroupController {

    // 현재 유저가 속한 그룹 조회
    @GetMapping
    @Operation(summary = "그룹 조회")
    public ResponseEntity<ApiResponse<ShowGroupResponse>> getGroup(
    ) {
        try {
            // Mock Data
            ShowGroupResponse response = ShowGroupResponse.builder()
                .groupIds(new ArrayList<>(List.of(
                    10001L, 10002L, 10003L,
                    10004L, 10005L, 10006L
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
    @Operation(summary = "그룹 생성")
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
    @Operation(summary = "그룹 내 일정 조회")
    @GetMapping("/schedule-groups/{id}")
    public ResponseEntity<ApiResponse<ShowGroupScheduleResponse>> getGroupSchedules(
        @RequestParam Long id
    ) {
        try {
            // 예외 발생
            // id가 db에 없다면 404_GROUP_NOT_FOUND
            if(
                id!=10001L && id!=10002L && id!=10003L &&
                id!=10004L && id!=10005L && id!=10006L
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 그룹을 찾을 수 없습니다."));
            }

            //Mock Data
            ShowGroupScheduleResponse response = ShowGroupScheduleResponse.builder()
                .scheduleIds(new ArrayList<>(List.of(
                    30001L, 30002L, 30003L,
                    30004L, 30005L
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
    @Operation(summary = "그룹 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DeleteGroupResponse>> deleteGroup(
        @RequestParam Long id,
        @RequestBody DeleteGroupRequest request
    ) {
        try {
            // 예외 발생
            // id가 db에 없다면 404_GROUP_NOT_FOUND
            if(
                id!=10001L && id!=10002L && id!=10003L &&
                    id!=10004L && id!=10005L && id!=10006L
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
    @Operation(summary = "그룹 멤버 조회")
    @GetMapping("/{id}/member")
    public ResponseEntity<ApiResponse<ShowGroupMemberResponse>> getGroupMembers(
        @RequestParam Long id
    ) {
        try {
            // 예외 발생
            // id가 db에 없다면 404_GROUP_NOT_FOUND
            if(
                id!=10001L && id!=10002L && id!=10003L &&
                    id!=10004L && id!=10005L && id!=10006L
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 그룹을 찾을 수 없습니다."));
            }

            // MockData
            ShowGroupMemberResponse response = ShowGroupMemberResponse.builder()
                .userIds(new ArrayList<>(List.of(
                    "KAKAO_1001", "KAKAO_1002", "KAKAO_1003", "KAKAO_1004", "KAKAO_1005",
                    "KAKAO_1006", "KAKAO_1007", "KAKAO_1008", "KAKAO_1009", "KAKAO_1010"
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
    @Operation(summary = "그룹 정보 수정")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ModifyGroupInfoResponse>> updateGroupInfo(
        @RequestParam Long id,
        @RequestBody ModifyGroupInfoRequest request
    ) {
        try {
            // 예외 발생
            // id가 db에 없다면 404_GROUP_NOT_FOUND
            if(
                id!=10001L && id!=10002L && id!=10003L &&
                    id!=10004L && id!=10005L && id!=10006L
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
    @Operation(summary = "그룹 멤버 내보내기")
    @PatchMapping("/{groupId}/members/{userId}")
    public ResponseEntity<ApiResponse<DeportGroupMemberResponse>> deportGroupMember(
        @RequestParam Long groupId,
        @RequestParam String userId
    ) {
        try {
            // 예외 발생
            // groupId가 db에 없다면 404_GROUP_NOT_FOUND
            if(
                groupId!=10001L && groupId!=10002L && groupId!=10003L &&
                    groupId!=10004L && groupId!=10005L && groupId!=10006L
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 그룹을 찾을 수 없습니다."));
            }
            // userId가 db에 없다면 404_USER_NOT_FOUND
            if(
                    !userId.equals("KAKAO_1001") && !userId.equals("KAKAO_1002") && !userId.equals("KAKAO_1003") && !userId.equals("KAKAO_1004") && !userId.equals("KAKAO_1005") &&
                    !userId.equals("KAKAO_1006") && !userId.equals("KAKAO_1007") && !userId.equals("KAKAO_1008") && !userId.equals("KAKAO_1009") && !userId.equals("KAKAO_1010") &&
                    !userId.equals("GOOGLE_1001") && !userId.equals("GOOGLE_1002") && !userId.equals("GOOGLE_1003") && !userId.equals("GOOGLE_1004") &&
                    !userId.equals("GOOGLE_1005") && !userId.equals("GOOGLE_1006") && !userId.equals("GOOGLE_1007") && !userId.equals("GOOGLE_1008")
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 유저를 찾을 수 없습니다."));
            }
            // userId가 해당 그룹에 없다면 404_USER_NOT_IN_GROUP
            if(
                !userId.equals("KAKAO_1001") && !userId.equals("KAKAO_1002") && !userId.equals("KAKAO_1003") && !userId.equals("KAKAO_1004") && !userId.equals("KAKAO_1005") &&
                    !userId.equals("KAKAO_1006") && !userId.equals("KAKAO_1007") && !userId.equals("KAKAO_1008") && !userId.equals("KAKAO_1009") && !userId.equals("KAKAO_1010")
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
    @Operation(summary = "멤버 그룹으로 초대")
    @PostMapping("/{id}/members")
    public ResponseEntity<ApiResponse<InviteGroupMemberResponse>> inviteGroupMember(
        @RequestParam Long id,
        @RequestBody InviteGroupMemberRequest request
    ) {
        try {
            // 예외 발생
            // groupId가 db에 없다면 404_GROUP_NOT_FOUND
            if(
                id!=10001L && id!=10002L && id!=10003L &&
                    id!=10004L && id!=10005L && id!=10006L
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 그룹을 찾을 수 없습니다."));
            }

            for(String tempUser: request.getUserIds()){
                // request의 userIds중 user들이 해당 그룹에 있다면 409_USER_ALREADY_IN_GROUP
                if(
                        tempUser.equals("KAKAO_1001") || tempUser.equals("KAKAO_1002") || tempUser.equals("KAKAO_1003") || tempUser.equals("KAKAO_1004") || tempUser.equals("KAKAO_1005") ||
                        tempUser.equals("KAKAO_1006") || tempUser.equals("KAKAO_1007") || tempUser.equals("KAKAO_1008") || tempUser.equals("KAKAO_1009") || tempUser.equals("KAKAO_1010")
                ){
                    return ResponseEntity.status(409)
                        .body(ApiResponse.error(ResponseCode.CONFLICT_REGISTER, "이미 해당 그룹에 존재하는 유저가 존재합니다."));
                }
                // request의 userIds중 user들이 db에 없다면 404_USER_NOT_FOUND
                else if(
                        !tempUser.equals("GOOGLE_1001") && !tempUser.equals("GOOGLE_1002") && !tempUser.equals("GOOGLE_1003") && !tempUser.equals("GOOGLE_1004") &&
                        !tempUser.equals("GOOGLE_1005") && !tempUser.equals("GOOGLE_1006") && !tempUser.equals("GOOGLE_1007") && !tempUser.equals("GOOGLE_1008")
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
    @Operation(summary = "그룹 멤버 권한 관리")
    @PatchMapping("/{id}/members")
    public ResponseEntity<ApiResponse<ControlGroupRoleResponse>> controlGroupRoles(
        @RequestParam Long id,
        @RequestBody ControlGroupRoleRequest request
    ) {
        try {
            // 예외 발생
            // groupId가 db에 없다면 404_GROUP_NOT_FOUND
            if(
                id!=10001L && id!=10002L && id!=10003L &&
                    id!=10004L && id!=10005L && id!=10006L
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 그룹을 찾을 수 없습니다."));
            }
            // request의 userIds중 user들이 db에 없다면 404_USER_NOT_FOUND
            if(
                    !request.getUserId().equals("KAKAO_1001") && !request.getUserId().equals("KAKAO_1002") && !request.getUserId().equals("KAKAO_1003") && !request.getUserId().equals("KAKAO_1004") && !request.getUserId().equals("KAKAO_1005") &&
                    !request.getUserId().equals("KAKAO_1006") && !request.getUserId().equals("KAKAO_1007") && !request.getUserId().equals("KAKAO_1008") && !request.getUserId().equals("KAKAO_1009") && !request.getUserId().equals("KAKAO_1010") &&
                    !request.getUserId().equals("GOOGLE_1001") && !request.getUserId().equals("GOOGLE_1002") && !request.getUserId().equals("GOOGLE_1003") && !request.getUserId().equals("GOOGLE_1004") &&
                    !request.getUserId().equals("GOOGLE_1005") && !request.getUserId().equals("GOOGLE_1006") && !request.getUserId().equals("GOOGLE_1007") && !request.getUserId().equals("GOOGLE_1008")
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 유저를 찾을 수 없습니다."));
            }
            // userId가 해당 그룹에 없다면 404_USER_NOT_IN_GROUP
            if(
                !request.getUserId().equals("KAKAO_1001") && !request.getUserId().equals("KAKAO_1002") && !request.getUserId().equals("KAKAO_1003") && !request.getUserId().equals("KAKAO_1004") && !request.getUserId().equals("KAKAO_1005") &&
                    !request.getUserId().equals("KAKAO_1006") && !request.getUserId().equals("KAKAO_1007") && !request.getUserId().equals("KAKAO_1008") && !request.getUserId().equals("KAKAO_1009") && !request.getUserId().equals("KAKAO_1010")
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
    @Operation(summary = "그룹 통계 조회")
    @GetMapping("/{id}/statistics")
    public ResponseEntity<ApiResponse<ShowGroupStatisticsResponse>> getGroupStatistics(
        @RequestParam Long id
    ) {
        try {
            // 예외 발생
            // groupId가 db에 없다면 404_GROUP_NOT_FOUND
            if(
                id!=10001L && id!=10002L && id!=10003L &&
                    id!=10004L && id!=10005L && id!=10006L
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 그룹을 찾을 수 없습니다."));
            }

            // MockData
            ShowGroupStatisticsResponse response = ShowGroupStatisticsResponse.builder()
                .groupUsers(new ArrayList<>(List.of(
                    new GroupUser("KAKAO_1001", GroupRole.GROUP_LEADER, new ArrayList<>(List.of(30000L, 30002L, 30003L, 30004L, 30005L))),
                    new GroupUser("KAKAO_1002", GroupRole.GROUP_MEMBER, new ArrayList<>(List.of(30000L, 30002L))),
                    new GroupUser("KAKAO_1003", GroupRole.GROUP_MEMBER, new ArrayList<>(List.of(30000L, 30003L, 30005L))),
                    new GroupUser("KAKAO_1004", GroupRole.GROUP_MEMBER, new ArrayList<>(List.of(30000L, 30002L, 30004L, 30005L))),
                    new GroupUser("KAKAO_1005", GroupRole.GROUP_MEMBER, new ArrayList<>(List.of(30003L, 30004L, 30005L))),
                    new GroupUser("KAKAO_1006", GroupRole.GROUP_MEMBER, new ArrayList<>(List.of(30000L, 30002L, 30003L, 30004L, 30005L))),
                    new GroupUser("KAKAO_1007", GroupRole.GROUP_MEMBER, new ArrayList<>(List.of(30000L, 30002L, 30003L))),
                    new GroupUser("KAKAO_1008", GroupRole.GROUP_MEMBER, new ArrayList<>(List.of(30000L))),
                    new GroupUser("KAKAO_1009", GroupRole.GROUP_MEMBER, new ArrayList<>(List.of(30000L, 30004L, 30005L))),
                    new GroupUser("KAKAO_1010", GroupRole.GROUP_MEMBER, new ArrayList<>(List.of(30003L, 30004L, 30005L)))
                )))
                .groupSchedules(new ArrayList<>(List.of(
                    new GroupSchedule(30001L, "인천역", LocalDateTime.now(), LocalDateTime.MAX),
                    new GroupSchedule(30002L, "국제금융센터-부산은행역", LocalDateTime.now(), LocalDateTime.MAX),
                    new GroupSchedule(30003L, "합정역", LocalDateTime.now(), LocalDateTime.MAX),
                    new GroupSchedule(30004L, "역삼역", LocalDateTime.now(), LocalDateTime.MAX),
                    new GroupSchedule(30005L, "서초역", LocalDateTime.now(), LocalDateTime.MAX)
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
    @Operation(summary = "일회성 일정을 그룹 일정으로 변경")
    @PatchMapping("/move-schedule")
    public ResponseEntity<ApiResponse<ScheduleToGroupResponse>> moveScheduleToGroup(
        @RequestBody ScheduleToGroupRequest request
    ) {
        try {
            // 예외 발생
            // request의 scheduleId가 이미 그룹에 있다면 409_SCHEDULE_ALREADY_IN_GROUP
                if(
                        request.getScheduleId()==30001L || request.getScheduleId()==30002L || request.getScheduleId()==30003L ||
                        request.getScheduleId()==30004L || request.getScheduleId()==30005L ||
                            request.getScheduleId()==31111L || request.getScheduleId()==32222L || request.getScheduleId()==33333L || request.getScheduleId()==34444L
                ){
                    return ResponseEntity.status(409)
                        .body(ApiResponse.error(ResponseCode.CONFLICT_REGISTER, "이미 그룹에 존재하는 일정 입니다."));
                }
                // request의 scheduleId가 db에 없다면 404_SCHEDULE_NOT_FOUND
                else if(
                        request.getScheduleId()!=35555L && request.getScheduleId()!=36666L && request.getScheduleId()!=37777L &&
                        request.getScheduleId()!=38888L && request.getScheduleId()!=39999L
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
    @Operation(summary = "그룹 탈퇴")
    @PatchMapping("/{id}/leave")
    public ResponseEntity<ApiResponse<WithdrawGroupResponse>> withdrawGroup(
        @RequestParam Long id
    ) {
        try {
            // 예외 발생
            // groupId가 db에 없다면 404_GROUP_NOT_FOUND
            if(
                id!=10001L && id!=10002L && id!=10003L &&
                    id!=10004L && id!=10005L && id!=10006L
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