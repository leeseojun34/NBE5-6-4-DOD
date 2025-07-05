package com.grepp.spring.app.controller.api.schedules;

import java.util.List;
import java.util.Map;
import javax.security.sasl.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schedules")
public class SchedulesControllerLkh {

    // 일정 등록
    @PostMapping
    public ResponseEntity<Map<String, String>> createScedules() {
        try {
            return ResponseEntity.ok(Map.of("message", "일정이 정상적으로 저장되었습니다."));

        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                return ResponseEntity.status(401).body(Map.of("message", "인증(로그인)이 되어있지 않습니다. 헤더에 Bearer {AccressToken}을 넘겼는지 확인해주세요."));
            }
            return ResponseEntity.status(400).body(Map.of("message", "필수값이 누락되었습니다."));
        }
    }

    // 일정 수정
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<Map<String, String>> modifyScedules() {
        try {
            return ResponseEntity.ok(Map.of("message", "일정이 정상적으로 수정되었습니다."));

        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                return ResponseEntity.status(401).body(Map.of("message", "인증(로그인)이 되어있지 않습니다. 헤더에 Bearer {AccressToken}을 넘겼는지 확인해주세요."));
            }
            return ResponseEntity.status(400).body(Map.of("message", "scheduleId 값이 누락되었습니다."));
        }
    }

    // 일정 확인
    @GetMapping("/{scheduleId}")
    public ResponseEntity<Map<String, Object >> showScedules() {

        Map<String, Object> schedule = Map.of(
            "eventId", 1,
            "startTime", "2025-07-05T14:00:00",
            "endTime", "2025-07-06T16:00:00",
            "status", "FIXED",
            "location", "강남역",
            "specificLocation", "강남역 스타벅스",
            "description", "DOD의 즐거운 미팅날",
            "meetingPlatform", "ZOOM",
            "platformUrl", "https://zoom.us/test-meeting"
        );

        return ResponseEntity.ok(Map.of("schedule", schedule));
    }

    // 일정 삭제
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Map<String, String>> deleteScedules() {

        try {
            return ResponseEntity.ok(Map.of("message", "일정이 정상적으로 삭제되었습니다."));

        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                return ResponseEntity.status(401).body(Map.of("message", "인증(로그인)이 되어있지 않습니다. 헤더에 Bearer {AccressToken}을 넘겼는지 확인해주세요."));
            }
            return ResponseEntity.status(400).body(Map.of("message", "scheduleId 값이 누락되었습니다."));
        }
    }

    // 출발장소 등록
    @PostMapping("depart-location/{memberId}")
    public ResponseEntity<Map<String, String>> createDepartLocation() {

        try {
            return ResponseEntity.ok(Map.of("message", "출발장소가 정상적으로 등록되었습니다."));

        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                return ResponseEntity.status(401).body(Map.of("message", "인증(로그인)이 되어있지 않습니다. 헤더에 Bearer {AccressToken}을 넘겼는지 확인해주세요."));
            }
            return ResponseEntity.status(400).body(Map.of("message", "필수값이 누락되었습니다."));
        }
    }

    // 중간장소 후보 조회
    @GetMapping("/{scheduleId}/suggested-locations")
    public ResponseEntity<Map<String, Object>> showSuggestedLocations() {

        // 첫 번째 지역 정보
        Map<String, Object> regionData1 = Map.of(
            "locationName", "강남역",
            "latitude", 37.4979,
            "longitude", 127.0276,
            "suggestedMemberId", "memberA",
            "voteCount", 5L,
            "status", "유력"
        );

        Map<String, Object> regionData2 = Map.of(
            "locationName", "역삼역",
            "latitude", 37.5008,
            "longitude", 127.0365,
            "suggestedMemberId", "memberB",
            "voteCount", 2L,
            "status", "default"
        );

        // 두 번째 지역 정보
        Map<String, Object> regionData3 = Map.of(
            "locationName", "홍대입구역",
            "latitude", 37.5572,
            "longitude", 126.9245,
            "suggestedMemberId", "memberC",
            "voteCount", 8L,
            "status", "당선"
        );

        // 지역별 리스트
        Map<String, List<Map<String, Object>>> middleLegions = Map.of(
            "middleRegionId_1", List.of(regionData1),
            "middleRegionId_2", List.of(regionData2),
            "middleRegionId_3", List.of(regionData3)
        );

        // 최종 응답
        Map<String, Object> response = Map.of("middleLegions", middleLegions);

        return ResponseEntity.ok(response);

    }

    // 출발 장소 지점 투표하기
    @PostMapping("/{scheduleId}/suggested-locations/vote/{memberId}")
    public ResponseEntity<Map<String, String>> voteMiddleLocation() {

        try {
            return ResponseEntity.ok(Map.of("message", "투표가 정상적으로 처리되었습니다."));

        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                return ResponseEntity.status(401).body(Map.of("message", "인증(로그인)이 되어있지 않습니다. 헤더에 Bearer {AccressToken}을 넘겼는지 확인해주세요."));
            }
            return ResponseEntity.status(400).body(Map.of("message", "필수값이 누락되었습니다."));
        }
    }

    // 중간 장소 지점 투표결과 조회
    @GetMapping("/{scheduleMemberId}/middle-location")
    public ResponseEntity<Map<String, Object >> showVoteResult() {

        Map<String, Object> voteResult = Map.of(
            "locationName", "홍대입구역",
            "latitude", 37.5572,
            "longitude", 126.9245,
            "voteCount", 8L
        );

        return ResponseEntity.ok(Map.of("voteResult", voteResult));
    }

    // 중간 장소(지하철 역) 지점 확인
    @GetMapping("/{scheduleId}/middle-location")
    public ResponseEntity<Map<String, Object >> showMiddleLocation() {

        Map<String, Object> middleLocation = Map.of(
            "locationName", "홍대입구역",
            "latitude", 37.5572,
            "longitude", 126.9245,
            "voteCount", 8L
        );

        return ResponseEntity.ok(Map.of("middleLocation", middleLocation));
    }

    // 온라인 회의장 링크 개설(줌, 구글미트)
    @PostMapping("/online-meeting")
    public ResponseEntity<Map<String, Object>> CreateOnlineMeeting() {
        Map<String, Object> onlineMeeting = Map.of(
            "message", "온라인 회의장이 개설되었습니다.",
            "platformURL", "https://zoom.us/ko/testRoom"
        );
        try {
            return ResponseEntity.ok(Map.of("onlineMeeting", onlineMeeting));

        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                return ResponseEntity.status(401).body(Map.of("message", "인증(로그인)이 되어있지 않습니다. 헤더에 Bearer {AccressToken}을 넘겼는지 확인해주세요."));
            }
            return ResponseEntity.status(400).body(Map.of("message", "필수값이 누락되었습니다."));
        }
    }

    // 오프라인 세부 장소 생성
    @PostMapping("/{scheduleId}/detail-locations")
    public ResponseEntity<Map<String, String>> CreateOfflineDetailLocation() {

        try {
            return ResponseEntity.ok(Map.of("message", "오프라인 세부 장소가 저장되었습니다."));

        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                return ResponseEntity.status(401).body(Map.of("message", "인증(로그인)이 되어있지 않습니다. 헤더에 Bearer {AccressToken}을 넘겼는지 확인해주세요."));
            }
            return ResponseEntity.status(400).body(Map.of("message", "scheduleId 값이 누락되었습니다."));
        }
    }

    // 공통 워크스페이스 등록
    @PostMapping("/{scheduleId}/workspaces")
    public ResponseEntity<Map<String, String>> CreateWorkspace() {

        try {
            return ResponseEntity.ok(Map.of("message", "워크스페이스 경로가 저장되었습니다."));

        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                return ResponseEntity.status(401).body(Map.of("message", "인증(로그인)이 되어있지 않습니다. 헤더에 Bearer {AccressToken}을 넘겼는지 확인해주세요."));
            }
            return ResponseEntity.status(400).body(Map.of("message", "scheduleId 값이 누락되었습니다."));
        }
    }

    // 오프라인 세부 장소 수정
    @PatchMapping("/{scheduleId}/detail-locations")
    public ResponseEntity<Map<String, String>> ModifyOfflineDetailLocation() {

        try {
            return ResponseEntity.ok(Map.of("message", "오프라인 미팅장소가 정상적으로 수정되었습니다."));

        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                return ResponseEntity.status(401).body(Map.of("message", "인증(로그인)이 되어있지 않습니다. 헤더에 Bearer {AccressToken}을 넘겼는지 확인해주세요."));
            }
            return ResponseEntity.status(400).body(Map.of("message", "scheduleId 값이 누락되었습니다."));
        }
    }

    // 공통 워크스페이스 수정
    @PatchMapping("/{schedueld}/workspaces")
    public ResponseEntity<Map<String, String>> ModifyWorkspace() {
        try {
            return ResponseEntity.ok(Map.of("message", "워크스페이스 경로가 정상적으로 수정되었습니다."));

        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                return ResponseEntity.status(401).body(Map.of("message", "인증(로그인)이 되어있지 않습니다. 헤더에 Bearer {AccressToken}을 넘겼는지 확인해주세요."));
            }
            return ResponseEntity.status(400).body(Map.of("message", "scheduleId 값이 누락되었습니다."));
        }
    }
}
