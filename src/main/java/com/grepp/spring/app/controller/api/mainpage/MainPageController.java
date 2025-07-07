package com.grepp.spring.app.controller.api.mainpage;

import com.grepp.spring.app.controller.api.mainpage.payload.ShowCalendarResponse;
import com.grepp.spring.app.controller.api.mainpage.payload.ShowGroupResponse;
import com.grepp.spring.app.controller.api.mainpage.payload.ShowScheduleListResponse;
import com.grepp.spring.app.model.schedule.domain.SCHEDULES_STATUS;
import com.grepp.spring.infra.response.ApiResponse;
import com.grepp.spring.infra.response.ResponseCode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MainPageController {

  // 일정 조회
  @GetMapping("/schedule-members/{id}")
  public ResponseEntity<ApiResponse<ShowScheduleListResponse>> showSchedules(@PathVariable Long id) {

    try {
      ShowScheduleListResponse response = new ShowScheduleListResponse();
      response.setScheduleId(30000L);
      response.setName("팀 회의");
      response.setStartTime(LocalDateTime.of(2025, 7, 8, 14, 0));
      response.setEndTime(LocalDateTime.of(2025, 7, 8, 16, 0));
      response.setSCHEDULES_STATUS(SCHEDULES_STATUS.FIXED);
      response.setDescription("주간 팀 회의 및 프로젝트 진행 상황 점검");
      response.setLocation("회의실 A");
      response.setCategory("업무");


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
  public ResponseEntity<ApiResponse<ShowGroupResponse>> showGroups() {

    try {
      ShowGroupResponse response = new ShowGroupResponse();
      response.setGroupIds(new ArrayList<>(Arrays.asList(
          10000L, 10001L, 10002L, 10003L, 10004L, 10005L
      )));



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
  public ResponseEntity<ApiResponse<ShowCalendarResponse>> showCalendarSchedules(
      @PathVariable Long calendarId,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

    try {
      // 구글 일정 데이터
      ShowCalendarResponse.CalendarSchedule googleSchedule1 = new ShowCalendarResponse.CalendarSchedule();
      googleSchedule1.setScheduleId(30001L);
      googleSchedule1.setCalendarId(300L);
      googleSchedule1.setTitle("백엔드 회의");
      googleSchedule1.setDescription("스프링 프로젝트 논의");
      googleSchedule1.setStartTime(LocalDateTime.of(2025, 7, 2, 15, 0));
      googleSchedule1.setEndTime(LocalDateTime.of(2025, 7, 2, 16, 0));
      googleSchedule1.setLocation("구글 미트");

      ShowCalendarResponse.CalendarSchedule googleSchedule2 = new ShowCalendarResponse.CalendarSchedule();
      googleSchedule2.setScheduleId(30002L);
      googleSchedule2.setCalendarId(301L);
      googleSchedule2.setTitle("프론트엔드 리뷰");
      googleSchedule2.setDescription("UI/UX 검토");
      googleSchedule2.setStartTime(LocalDateTime.of(2025, 7, 3, 10, 0));
      googleSchedule2.setEndTime(LocalDateTime.of(2025, 7, 3, 11, 30));
      googleSchedule2.setLocation("온라인");

      List<ShowCalendarResponse.CalendarSchedule> googleSchedules = Arrays.asList(
          googleSchedule1, googleSchedule2
      );

      // 내부 일정 데이터
      ShowCalendarResponse.CalendarSchedule internalSchedule1 = new ShowCalendarResponse.CalendarSchedule();
      internalSchedule1.setScheduleId(30003L);
      internalSchedule1.setCalendarId(300L);
      internalSchedule1.setTitle("team hmd 정모");
      internalSchedule1.setDescription("팀 정기 모임");
      internalSchedule1.setStartTime(LocalDateTime.of(2025, 7, 3, 10, 0));
      internalSchedule1.setEndTime(LocalDateTime.of(2025, 7, 3, 12, 0));
      internalSchedule1.setLocation("강남역");

      ShowCalendarResponse.CalendarSchedule internalSchedule2 = new ShowCalendarResponse.CalendarSchedule();
      internalSchedule2.setScheduleId(30005L);
      internalSchedule2.setCalendarId(301L);
      internalSchedule2.setTitle("코드 리뷰");
      internalSchedule2.setDescription("주간 코드 리뷰 세션");
      internalSchedule2.setStartTime(LocalDateTime.of(2025, 7, 4, 14, 0));
      internalSchedule2.setEndTime(LocalDateTime.of(2025, 7, 4, 15, 30));
      internalSchedule2.setLocation("개발실");

      List<ShowCalendarResponse.CalendarSchedule> internalSchedules = Arrays.asList(
          internalSchedule1, internalSchedule2
      );
      // === 날짜 필터링 적용 ===
      if (date != null) {
        googleSchedules = googleSchedules.stream()
            .filter(s -> s.getStartTime().toLocalDate().equals(date))
            .collect(Collectors.toList());

        internalSchedules = internalSchedules.stream()
            .filter(s -> s.getStartTime().toLocalDate().equals(date))
            .collect(Collectors.toList());
      }


      // === 응답 구성 ===
      ShowCalendarResponse response = new ShowCalendarResponse();
      response.setMessage("일정 목록 조회에 성공했습니다.");
      response.setGoogleSchedules(googleSchedules);
      response.setInternalSchedules(internalSchedules);


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