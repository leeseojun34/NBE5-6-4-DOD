package com.grepp.spring.app.controller.api.mainpage;

import com.grepp.spring.infra.response.ApiResponse;
import com.grepp.spring.infra.response.ResponseCode;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MainPageController {

  // 일정 조회
  @GetMapping("/schedule-members/{id}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> showSchedules(@PathVariable Long id) {

    try {
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

      Map<String, Object> response = Map.of("schedule", schedule);
      return ResponseEntity.ok(ApiResponse.success(response));

    } catch (AuthenticationException e) {
      return ResponseEntity.status(401)
          .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "인증(로그인)이 되어있지 않습니다."));
    } catch (Exception e) {
      return ResponseEntity.status(400)
          .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "필수값이 누락되었거나 잘못된 요청입니다."));
    }
  }

  // 그룹 조회
  @GetMapping("/groups")
  public ResponseEntity<ApiResponse<Map<String, List<Integer>>>> showGroups() {

    try {
      List<Integer> groups = List.of(10000, 10001, 10003, 10005);
      Map<String, List<Integer>> response = Map.of("groups", groups);
      return ResponseEntity.ok(ApiResponse.success(response));

    } catch (AuthenticationException e) {
      return ResponseEntity.status(401)
          .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "인증(로그인)이 되어있지 않습니다."));
    } catch (Exception e) {
      return ResponseEntity.status(400)
          .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "필수값이 누락되었거나 잘못된 요청입니다."));
    }
  }

  // 외부 캘린더 (구글) 조회
  @GetMapping("/calendar/{calendarId}/schedules")
  public ResponseEntity<ApiResponse<Map<String, Object>>> showCalendarSchedules(@PathVariable Long calendarId) {

    try {
      List<Map<String, Object>> googleSchedules = List.of(
          Map.of(
              "title", "백엔드 회의",
              "start_datetime", "2025-07-02T15:00:00",
              "end_datetime", "2025-07-02T16:00:00"
          )
      );

      List<Map<String, Object>> internalSchedules = List.of(
          Map.of(
              "title", "team hmd 정모",
              "start_datetime", "2025-07-03T10:00:00",
              "end_datetime", "2025-07-03T12:00:00"
          )
      );

      Map<String, Object> data = Map.of(
          "googleSchedules", googleSchedules,
          "internalSchedules", internalSchedules
      );

      Map<String, Object> response = Map.of(
          "message", "일정 목록 조회에 성공했습니다.",
          "data", data
      );

      return ResponseEntity.ok(ApiResponse.success(response));

    } catch (AuthenticationException e) {
      return ResponseEntity.status(401)
          .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "인증(로그인)이 되어있지 않습니다."));
    } catch (Exception e) {
      return ResponseEntity.status(400)
          .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "필수값이 누락되었거나 잘못된 요청입니다."));
    }
  }
}