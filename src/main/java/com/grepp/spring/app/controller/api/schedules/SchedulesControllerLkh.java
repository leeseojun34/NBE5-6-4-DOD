package com.grepp.spring.app.controller.api.schedules;

import com.grepp.spring.app.controller.api.schedules.payload.CreateDepartLocationRequest;
import com.grepp.spring.app.controller.api.schedules.payload.CreateOfflineDetailLocationsRequest;
import com.grepp.spring.app.controller.api.schedules.payload.CreateOnlineMeetingRequest;
import com.grepp.spring.app.controller.api.schedules.payload.CreateOnlineMeetingResponse;
import com.grepp.spring.app.controller.api.schedules.payload.CreateSchedulesRequest;
import com.grepp.spring.app.controller.api.schedules.payload.CreateWorkspaceRequest;
import com.grepp.spring.app.controller.api.schedules.payload.ModifyOfflineDetailLocationsRequest;
import com.grepp.spring.app.controller.api.schedules.payload.ModifySchedulesRequest;
import com.grepp.spring.app.controller.api.schedules.payload.ModifyWorkspaceRequest;
import com.grepp.spring.app.controller.api.schedules.payload.ShowMiddleLocationResponse;
import com.grepp.spring.app.controller.api.schedules.payload.ShowSchedulesResponse;
import com.grepp.spring.app.controller.api.schedules.payload.ShowSuggestedLocationsResponse;
import com.grepp.spring.app.controller.api.schedules.payload.VoteMiddleLocationsRequest;
import com.grepp.spring.app.model.schedule.domain.MEETING_PLATFORM;
import com.grepp.spring.app.model.schedule.domain.SCHEDULES_STATUS;
import com.grepp.spring.app.model.schedule.domain.VOTE_STATUS;
import com.grepp.spring.infra.error.exceptions.AuthApiException;
import com.grepp.spring.infra.response.ApiResponse;
import com.grepp.spring.infra.response.ResponseCode;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schedules")
public class SchedulesControllerLkh {

    // 일정 등록
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createScedules(@RequestBody CreateSchedulesRequest request) {

        try {

            if(
                request.getEventId()!= 20000L && request.getEventId()!=20001L && request.getEventId()!=20002L &&
                    request.getEventId()!=20003L && request.getEventId()!=20004L && request.getEventId()!=22222L
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 이벤트를 찾을 수 없습니다."));
            }


            return ResponseEntity.ok(ApiResponse.noContent());
        }
         catch (Exception e) {
             if (e instanceof AuthApiException) {
                 return ResponseEntity.status(401)
                     .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
             }

             return ResponseEntity.status(400)
                 .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
         }
    }

    // 일정 수정
    @PatchMapping("/modify/{scheduleId}")
    public ResponseEntity<ApiResponse> modifyScedules(@PathVariable Long scheduleId, @RequestBody ModifySchedulesRequest request) {
        try {

            if (scheduleId !=30000 && scheduleId !=30001 && scheduleId !=30002 && scheduleId !=30003 && scheduleId !=30005 && scheduleId !=30303 && scheduleId != 33333) {
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 이벤트를 찾을 수 없습니다."));
            }
            return ResponseEntity.ok(ApiResponse.noContent());
        }

         catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
    }

    // 일정 확인
    @GetMapping("/show/{scheduleId}")
    public ResponseEntity<ApiResponse<ShowSchedulesResponse>> showScedules(@PathVariable Long scheduleId) {

        try {

            if (scheduleId !=30000 && scheduleId !=30001 && scheduleId !=30002 && scheduleId !=30003 && scheduleId !=30005 && scheduleId !=30303 && scheduleId != 33333) {
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 이벤트를 찾을 수 없습니다."));
            }

            ShowSchedulesResponse  response = new ShowSchedulesResponse();
            response.setEventId(1L);
            response.setStartTime(LocalDateTime.of(2025, 7, 6, 3, 7));
            response.setEndTime(LocalDateTime.of(2025, 7, 7, 3, 7));
            response.setSCHEDULES_STATUS(SCHEDULES_STATUS.FIXED);
            response.setLocation("강남역");
            response.setSpecificLocation("강남역 스타벅스");
            response.setDescription("DOD의 즐거운 미팅 날");
            response.setMeetingPlatform(MEETING_PLATFORM.ZOOM);
            response.setPlatformUrl("https://zoom.us/test-meeting");

            return ResponseEntity.ok(ApiResponse.success(response));
        }

         catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
    }

    // 일정 삭제
    @DeleteMapping("/delete/{scheduleId}")
    public ResponseEntity<ApiResponse> deleteScedules(@PathVariable Long scheduleId) {

        try {
            if (scheduleId !=30000 && scheduleId !=30001 && scheduleId !=30002 && scheduleId !=30003 && scheduleId !=30005 && scheduleId !=30303 && scheduleId != 33333) {
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 이벤트를 찾을 수 없습니다."));
            }
            return ResponseEntity.ok(ApiResponse.noContent());
        }
           catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
    }

    // 출발장소 등록
    @PostMapping("create-depart-location/{memberId}")
    public ResponseEntity<ApiResponse> createDepartLocation(@PathVariable String memberId, @RequestBody CreateDepartLocationRequest request) {

        try {
            if(
                !memberId.equals("KAKAO_1234") && !memberId.equals("KAKAO_5678") && !memberId.equals("GOOGLE_1234") && !memberId.equals("GOOGLE_5678")
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 이벤트를 찾을 수 없습니다."));
            }
            if(
                request.getScheduleId()!= 30000L && request.getScheduleId()!=30001L && request.getScheduleId()!=30002L &&
                    request.getScheduleId()!=30003L && request.getScheduleId()!=30005L && request.getScheduleId()!=30303L && request.getScheduleId()!=33333L
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 이벤트를 찾을 수 없습니다."));
            }

            return ResponseEntity.ok(ApiResponse.noContent());
        }

           catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
    }


    // 중간장소 후보 조회
    @GetMapping("/show-suggested-locations{scheduleId}")
    public ResponseEntity<ApiResponse<List<ShowSuggestedLocationsResponse>>> showSuggestedLocations(@PathVariable Long scheduleId) {

        try {
            if (scheduleId !=30000 && scheduleId !=30001 && scheduleId !=30002 && scheduleId !=30003 && scheduleId !=30005 && scheduleId !=30303 && scheduleId != 33333) {
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 이벤트를 찾을 수 없습니다."));
            }

            ShowSuggestedLocationsResponse response1 = new ShowSuggestedLocationsResponse();
            response1.setLocationName("강남역");
            response1.setLatitude(37.4979);
            response1.setLongitude(127.0276);
            response1.setSuggestedMemberId(1L);
            response1.setVoteCount(5L);
            response1.setSCHEDULES_STATUS(VOTE_STATUS.ALMOST);

            ShowSuggestedLocationsResponse response2 = new ShowSuggestedLocationsResponse();
            response2.setLocationName("역삼역");
            response2.setLatitude(37.5008);
            response2.setLongitude(127.0365);
            response2.setSuggestedMemberId(2L);
            response2.setVoteCount(2L);
            response2.setSCHEDULES_STATUS(VOTE_STATUS.DEFAULT);

            ShowSuggestedLocationsResponse response3 = new ShowSuggestedLocationsResponse();
            response3.setLocationName("홍대입구역");
            response3.setLatitude(37.5572);
            response3.setLongitude(126.9245);
            response3.setSuggestedMemberId(3L);
            response3.setVoteCount(8L);
            response3.setSCHEDULES_STATUS(VOTE_STATUS.WINNER);

            List<ShowSuggestedLocationsResponse> list = List.of(response1, response2, response3);

            return ResponseEntity.ok(ApiResponse.success(list));
        }

           catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }

    }

    // 출발 장소 지점 투표하기
    @PostMapping("/suggested-locations/vote/{scheduleId}")
    public ResponseEntity<ApiResponse> voteMiddleLocation(@PathVariable Long scheduleId, @RequestBody VoteMiddleLocationsRequest request) {

        try {

            if (scheduleId !=30000 && scheduleId !=30001 && scheduleId !=30002 && scheduleId !=30003 && scheduleId !=30005 && scheduleId !=30303 && scheduleId != 33333) {
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 이벤트를 찾을 수 없습니다."));
            }

            if (request.getLocationId() !=40000 && request.getLocationId() !=40001 && request.getLocationId() !=40004 && request.getLocationId() !=40404 && request.getLocationId() !=44444 ) {
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 이벤트를 찾을 수 없습니다."));
            }

            return ResponseEntity.ok(ApiResponse.noContent());

        }

           catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
    }

//    // 중간 장소 지점 투표결과 조회
//    @GetMapping("/show-vote-result/{scheduleMemberId}")
//    public ResponseEntity<ApiResponse<ShowVoteResultResponse>> showVoteResult(@PathVariable Long scheduleMemberId, @RequestBody ShowVoteResultRequest request) {
//
//        try {
//            ShowVoteResultResponse response = new ShowVoteResultResponse();
//            response.setLocationName("홍대입구역");
//            response.setLatitude(37.5572);
//            response.setLongitude(126.9245);
//            response.setVoteCount(8L);
//
//            return ResponseEntity.ok(ApiResponse.success(response));
//        }
//
//           catch (Exception e) {
//            if (e instanceof AuthApiException) {
//                return ResponseEntity.status(401)
//                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
//            }
//            return ResponseEntity.status(400)
//                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
//        }
//    }

    // 중간 장소(지하철 역) 지점 확인 && 중간 장소 지점 투표결과 조회
    @GetMapping("/show-middle-location/{scheduleId}")
    public ResponseEntity<ApiResponse<ShowMiddleLocationResponse>> showMiddleLocation(@PathVariable Long scheduleId) {

        try {

            if (scheduleId !=30000 && scheduleId !=30001 && scheduleId !=30002 && scheduleId !=30003 && scheduleId !=30005 && scheduleId !=30303 && scheduleId != 33333) {
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 이벤트를 찾을 수 없습니다."));
            }

            ShowMiddleLocationResponse response = new ShowMiddleLocationResponse();
            response.setLocationName("홍대입구역");
            response.setLatitude(37.5572);
            response.setLongitude(126.9245);
            response.setVoteCount(8L);

            return ResponseEntity.ok(ApiResponse.success(response));
        }

           catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
    }

    // 온라인 회의장 링크 개설(줌, 구글미트)
    @PostMapping("/create-online-meeting")
    public ResponseEntity<ApiResponse<CreateOnlineMeetingResponse>> CreateOnlineMeeting(@RequestBody CreateOnlineMeetingRequest request) {

        try {

            if(
                request.getScheduleId()!= 30000L && request.getScheduleId()!=30001L && request.getScheduleId()!=30002L &&
                    request.getScheduleId()!=30003L && request.getScheduleId()!=30005L && request.getScheduleId()!=30303L && request.getScheduleId()!=33333L
            ){
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 이벤트를 찾을 수 없습니다."));
            }

            CreateOnlineMeetingResponse response = new CreateOnlineMeetingResponse();
            response.setPlatformURL("https://zoom.us/ko/testRoom");

            return ResponseEntity.ok(ApiResponse.success(response));
        }

           catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
    }

    // 오프라인 세부 장소 생성
    @PostMapping("/create-detail-locations/{scheduleId}")
    public ResponseEntity<ApiResponse> CreateOfflineDetailLocation(@PathVariable Long scheduleId, @RequestBody CreateOfflineDetailLocationsRequest request) {

        try {

            if (scheduleId !=30000 && scheduleId !=30001 && scheduleId !=30002 && scheduleId !=30003 && scheduleId !=30005 && scheduleId !=30303 && scheduleId != 33333) {
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 이벤트를 찾을 수 없습니다."));
            }

            return ResponseEntity.ok(ApiResponse.noContent());
        }

           catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
    }

    // 공통 워크스페이스 등록
    @PostMapping("/add-workspaces/{scheduleId}")
    public ResponseEntity<ApiResponse> CreateWorkspace(@PathVariable Long scheduleId, @RequestBody CreateWorkspaceRequest request) {

        try {
            if (scheduleId !=30000 && scheduleId !=30001 && scheduleId !=30002 && scheduleId !=30003 && scheduleId !=30005 && scheduleId !=30303 && scheduleId != 33333) {
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 이벤트를 찾을 수 없습니다."));
            }

            return ResponseEntity.ok(ApiResponse.noContent());
        }
           catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
    }

    // 오프라인 세부 장소 수정
    @PatchMapping("/modify-detail-locations/{scheduleId}")
    public ResponseEntity<ApiResponse> ModifyOfflineDetailLocation(@PathVariable Long scheduleId, @RequestBody ModifyOfflineDetailLocationsRequest request) {

        try {

            if (scheduleId !=30000 && scheduleId !=30001 && scheduleId !=30002 && scheduleId !=30003 && scheduleId !=30005 && scheduleId !=30303 && scheduleId != 33333) {
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 이벤트를 찾을 수 없습니다."));
            }

            return ResponseEntity.ok(ApiResponse.noContent());
        }

           catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
    }

    // 공통 워크스페이스 수정
    @PatchMapping("/modify-workspaces/{schedueld}")
    public ResponseEntity<ApiResponse> ModifyWorkspace(@PathVariable Long scheduleId, @RequestBody ModifyWorkspaceRequest request) {

        try {
            if (scheduleId !=30000 && scheduleId !=30001 && scheduleId !=30002 && scheduleId !=30003 && scheduleId !=30005 && scheduleId !=30303 && scheduleId != 33333) {
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 이벤트를 찾을 수 없습니다."));
            }

            return ResponseEntity.ok(ApiResponse.noContent());
        }

           catch (Exception e) {
            if (e instanceof AuthApiException) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "서버가 요청을 처리할 수 없습니다."));
        }
    }
}