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
import com.grepp.spring.app.controller.api.schedules.payload.ShowVoteResultRequest;
import com.grepp.spring.app.controller.api.schedules.payload.ShowVoteResultResponse;
import com.grepp.spring.app.controller.api.schedules.payload.VoteMiddleLocationsRequest;
import com.grepp.spring.app.model.schedule.domain.MEETING_PLATFORM;
import com.grepp.spring.app.model.schedule.domain.SCHEDULES_STATUS;
import com.grepp.spring.app.model.schedule.domain.VOTE_STATUS;
import com.grepp.spring.infra.response.ApiResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javax.security.sasl.AuthenticationException;
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
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    // 일정 수정
    @PatchMapping("/modify/{scheduleId}")
    public ResponseEntity<ApiResponse> modifyScedules(@PathVariable Long scheduleId, @RequestBody ModifySchedulesRequest request) {
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    // 일정 확인
    @GetMapping("/show/{scheduleId}")
    public ResponseEntity<ApiResponse<ShowSchedulesResponse>> showScedules(@PathVariable Long scheduleId) {

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

    // 일정 삭제
    @DeleteMapping("/delete/{scheduleId}")
    public ResponseEntity<ApiResponse> deleteScedules(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    // 출발장소 등록
    @PostMapping("create-depart-location/{memberId}")
    public ResponseEntity<ApiResponse> createDepartLocation(@PathVariable Long memberId, @RequestBody CreateDepartLocationRequest request) {
        return ResponseEntity.ok(ApiResponse.noContent());
    }


    // 중간장소 후보 조회
    @GetMapping("/show-suggested-locations{scheduleId}")
    public ResponseEntity<ApiResponse<List<ShowSuggestedLocationsResponse>>> showSuggestedLocations(@PathVariable Long scheduleId) {

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

    // 출발 장소 지점 투표하기
    @PostMapping("/suggested-locations/vote/{scheduleId}")
    public ResponseEntity<ApiResponse> voteMiddleLocation(@PathVariable Long scheduleId, @RequestBody VoteMiddleLocationsRequest request) {
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    // 중간 장소 지점 투표결과 조회
    @GetMapping("/show-vote-result/{scheduleMemberId}")
    public ResponseEntity<ApiResponse<ShowVoteResultResponse>> showVoteResult(@PathVariable Long scheduleMemberId, @RequestBody ShowVoteResultRequest request) {
        ShowVoteResultResponse response = new ShowVoteResultResponse();
        response.setLocationName("홍대입구역");
        response.setLatitude(37.5572);
        response.setLongitude(126.9245);
        response.setVoteCount(8L);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 중간 장소(지하철 역) 지점 확인
    @GetMapping("/show-middle-location/{scheduleId}")
    public ResponseEntity<ApiResponse<ShowMiddleLocationResponse>> showMiddleLocation(@PathVariable Long scheduleId) {

        ShowMiddleLocationResponse response = new ShowMiddleLocationResponse();
        response.setLocationName("홍대입구역");
        response.setLatitude(37.5572);
        response.setLongitude(126.9245);
        response.setVoteCount(8L);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 온라인 회의장 링크 개설(줌, 구글미트)
    @PostMapping("/create-online-meeting")
    public ResponseEntity<ApiResponse<CreateOnlineMeetingResponse>> CreateOnlineMeeting(@RequestBody CreateOnlineMeetingRequest request) {
        CreateOnlineMeetingResponse response = new CreateOnlineMeetingResponse();
        response.setPlatformURL("https://zoom.us/ko/testRoom");

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 오프라인 세부 장소 생성
    @PostMapping("/create-detail-locations/{scheduleId}")
    public ResponseEntity<ApiResponse> CreateOfflineDetailLocation(@PathVariable Long scheduleId, @RequestBody CreateOfflineDetailLocationsRequest request) {
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    // 공통 워크스페이스 등록
    @PostMapping("/add-workspaces/{scheduleId}")
    public ResponseEntity<ApiResponse> CreateWorkspace(@PathVariable Long scheduleId, @RequestBody CreateWorkspaceRequest request) {
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    // 오프라인 세부 장소 수정
    @PatchMapping("/modify-detail-locations/{scheduleId}")
    public ResponseEntity<ApiResponse> ModifyOfflineDetailLocation(@PathVariable Long scheduleId, @RequestBody ModifyOfflineDetailLocationsRequest request) {
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    // 공통 워크스페이스 수정
    @PatchMapping("/modify-workspaces/{schedueld}")
    public ResponseEntity<ApiResponse> ModifyWorkspace(@PathVariable Long scheduleId, @RequestBody ModifyWorkspaceRequest request) {
        return ResponseEntity.ok(ApiResponse.noContent());
    }
}
