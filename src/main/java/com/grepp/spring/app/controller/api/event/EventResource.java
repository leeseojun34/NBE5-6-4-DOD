package com.grepp.spring.app.controller.api.event;

import com.grepp.spring.app.controller.api.event.payload.*;
import com.grepp.spring.infra.response.ApiResponse;
import com.grepp.spring.infra.response.ResponseCode;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/events", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventResource {

    // 이벤트 생성
    @PostMapping
    public ResponseEntity<ApiResponse<EventCreateResponse>> createEvent(@RequestBody @Valid EventCreateRequest request) {

        return ResponseEntity.status(201)
            .body(ApiResponse.success("이벤트가 성공적으로 생성되었습니다."));
    }

    // 이벤트 수정
    @PatchMapping("/{eventId}")
    public ResponseEntity<ApiResponse<EventUpdateResponse>> updateEvent(
        @PathVariable Long eventId,
        @RequestBody @Valid EventUpdateRequest request) {

        try {
            return ResponseEntity.ok(ApiResponse.success("이벤트가 성공적으로 수정되었습니다."));
        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                return ResponseEntity.status(401).body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400).body(ApiResponse.error(ResponseCode.BAD_REQUEST, "eventId 값이 유효하지 않습니다."));
        }
    }

    // 이벤트 일정 참여
    @PostMapping("/{eventId}")
    public ResponseEntity<ApiResponse<JoinEventResponse>> joinEvent(
        @PathVariable Long eventId) {

        try {
            JoinEventResponse response = new JoinEventResponse();
            response.setRole("MEMBER");
            response.setJoinedAt(LocalDateTime.now());

            return ResponseEntity.ok(ApiResponse.success("이벤트에 성공적으로 참여했습니다."));
        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                return ResponseEntity.status(401).body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400).body(ApiResponse.error(ResponseCode.BAD_REQUEST, "eventId 값이 유효하지 않습니다."));
        }

    }

    // 이벤트 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<EventListResponse>> getEventList() {

        try {
            EventListResponse response = new EventListResponse();
            response.setTotalCount(2);
            List<EventListResponse.EventList> events = new ArrayList<>();

            EventListResponse.EventList event1 = new EventListResponse.EventList();
            event1.setEventId(1L);
            event1.setGroupId(1L);
            event1.setTitle("그룹 미팅");
            event1.setDescription("Q3 계획 수립을 위한 팀 미팅 1");
            event1.setMeetingType("ONLINE");
            event1.setMaxMember(5);
            event1.setCurrentMember(3);
            event1.setCreatedAt(LocalDateTime.of(2025, 7, 5, 9, 0));
            event1.setIsGroupEvent(true);
            event1.setGroupName("개발팀");

            EventListResponse.EventList event2 = new EventListResponse.EventList();
            event2.setEventId(2L);
            event2.setGroupId(null);
            event2.setTitle("일회성 미팅");
            event2.setDescription("Q3 계획 수립을 위한 팀 미팅 2");
            event2.setMeetingType("OFFLINE");
            event2.setMaxMember(10);
            event2.setCurrentMember(7);
            event2.setCreatedAt(LocalDateTime.of(2025, 7, 8, 16, 30));
            event2.setIsGroupEvent(false);
            event2.setGroupName(null);
            events.add(event1);
            events.add(event2);

            response.setEvents(events);

            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                return ResponseEntity.status(401).body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400).body(ApiResponse.error(ResponseCode.NOT_FOUND, "이벤트 목록을 조회하는데 실패했습니다."));
        }

    }

    // 개인의 가능한 시간대 생성/수정
    @PostMapping("/{eventId}/my-time")
    public ResponseEntity<ApiResponse<MyTimeScheduleResponse>> createOrUpdateMyTime(
        @PathVariable Long eventId,
        @RequestBody @Valid MyTimeScheduleRequest request) {

        try {
            return ResponseEntity.ok(ApiResponse.success("개인 일정이 성공적으로 생성/수정되었습니다."));
        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                return ResponseEntity.status(401).body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400).body(ApiResponse.error(ResponseCode.BAD_REQUEST, "eventId 값이 유효하지 않습니다."));
        }
    }

    // 참여자 전원의 가능한 시간대 조회
    @GetMapping("/{eventId}/all-time")
    public ResponseEntity<ApiResponse<AllTimeScheduleResponse>> getAllTimeSchedules(@PathVariable Long eventId) {

        try {
            AllTimeScheduleResponse response = new AllTimeScheduleResponse();
            response.setEventId(eventId);
            response.setEventTitle("시이바 카츠오모이");
            response.setTotalMembers(3);
            response.setConfirmedMembers(2);

            // 멤버별 스케줄 정보
            List<AllTimeScheduleResponse.MemberSchedule> memberSchedules = new ArrayList<>();

            // 첫 번째 멤버 (나의 가능한 시간)
            AllTimeScheduleResponse.MemberSchedule member1 = new AllTimeScheduleResponse.MemberSchedule();
            member1.setEventMemberId(1001L);
            member1.setMemberName("나의 가능한 시간");
            member1.setRole("HOST");
            member1.setIsConfirmed(true);

            List<AllTimeScheduleResponse.DailyTimeSlot> member1Slots = new ArrayList<>();
            for (int i = 13; i <= 19; i++) {
                AllTimeScheduleResponse.DailyTimeSlot slot = new AllTimeScheduleResponse.DailyTimeSlot();
                LocalDate date = LocalDate.of(2025, 7, i);
                slot.setDate(date);
                slot.setDayOfWeek(date.getDayOfWeek().toString().substring(0, 3).toUpperCase());
                slot.setDisplayDate(String.format("07/%02d", i));

                // Mock 데이터: 10-12시 시간대에 가능 (비트 20~23: 10:00, 10:30, 11:00, 11:30)
                slot.setTimeBit(0b111100000000000000000000L); // 20~23번째 비트
                member1Slots.add(slot);
            }
            member1.setDailyTimeSlots(member1Slots);

            // 두 번째 멤버 (모두 가능한 시간)
            AllTimeScheduleResponse.MemberSchedule member2 = new AllTimeScheduleResponse.MemberSchedule();
            member2.setEventMemberId(1002L);
            member2.setMemberName("모두 가능한 시간");
            member2.setRole("MEMBER");
            member2.setIsConfirmed(true);

            List<AllTimeScheduleResponse.DailyTimeSlot> member2Slots = new ArrayList<>();
            for (int i = 13; i <= 19; i++) {
                AllTimeScheduleResponse.DailyTimeSlot slot = new AllTimeScheduleResponse.DailyTimeSlot();
                LocalDate date = LocalDate.of(2025, 7, i);
                slot.setDate(date);
                slot.setDayOfWeek(date.getDayOfWeek().toString().substring(0, 3).toUpperCase());
                slot.setDisplayDate(String.format("07/%02d", i));

                // Mock 데이터: 다양한 시간대에 가능
                if (i == 13) { // 월요일: 10-11시
                    slot.setTimeBit(0b110000000000000000000000L);
                } else if (i == 15) { // 수요일: 10-12시
                    slot.setTimeBit(0b111100000000000000000000L);
                } else if (i == 17) { // 금요일: 11-12시
                    slot.setTimeBit(0b001100000000000000000000L);
                } else {
                    slot.setTimeBit(0L); // 불가능
                }
                member2Slots.add(slot);
            }
            member2.setDailyTimeSlots(member2Slots);

            // 세 번째 멤버 (아직 확정 안함)
            AllTimeScheduleResponse.MemberSchedule member3 = new AllTimeScheduleResponse.MemberSchedule();
            member3.setEventMemberId(1003L);
            member3.setMemberName("박민수");
            member3.setRole("MEMBER");
            member3.setIsConfirmed(false);

            List<AllTimeScheduleResponse.DailyTimeSlot> member3Slots = new ArrayList<>();
            for (int i = 13; i <= 19; i++) {
                AllTimeScheduleResponse.DailyTimeSlot slot = new AllTimeScheduleResponse.DailyTimeSlot();
                LocalDate date = LocalDate.of(2025, 7, i);
                slot.setDate(date);
                slot.setDayOfWeek(date.getDayOfWeek().toString().substring(0, 3).toUpperCase());
                slot.setDisplayDate(String.format("07/%02d", i));
                slot.setTimeBit(0L); // 아직 시간 입력 안함
                member3Slots.add(slot);
            }
            member3.setDailyTimeSlots(member3Slots);

            memberSchedules.add(member1);
            memberSchedules.add(member2);
            memberSchedules.add(member3);
            response.setMemberSchedules(memberSchedules);

            return ResponseEntity.ok(ApiResponse.success(response));

        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                return ResponseEntity.status(401).body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400).body(ApiResponse.error(ResponseCode.BAD_REQUEST, "eventId 값이 유효하지 않습니다."));
        }
    }

    // 개인의 가능한 시간대 확정
    @PostMapping("/{eventId}/complete")
    public ResponseEntity<ApiResponse<CompleteMyTimeResponse>> completeMyTime(@PathVariable Long eventId) {

        try {
            return ResponseEntity.ok(ApiResponse.success("개인 일정이 성공적으로 확정되었습니다."));
        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                return ResponseEntity.status(401).body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400).body(ApiResponse.error(ResponseCode.BAD_REQUEST, "eventId 값이 유효하지 않습니다."));
        }
    }

    // 이벤트 조율 결과 생성
    @PostMapping("/events/{eventId}/result")
    public ResponseEntity<ApiResponse<CreateScheduleResultResponse>> createScheduleResult(
        @PathVariable Long eventId,
        @RequestBody @Valid CreateScheduleResultRequest request) {

        try {
            return ResponseEntity.ok(ApiResponse.success("일정 조율 결과가 성공적으로 생성되었습니다."));
        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                return ResponseEntity.status(401).body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400).body(ApiResponse.error(ResponseCode.BAD_REQUEST, "eventId 값이 유효하지 않습니다."));
        }
    }

    // 이벤트 조율 결과 조회
    @GetMapping("/events/{eventId}/result")
    public ResponseEntity<ApiResponse<ScheduleResultResponse>> getScheduleResult(@PathVariable Long eventId) {

        try {
            ScheduleResultResponse response = new ScheduleResultResponse();
            response.setEventTitle("카츠오모이 가는날");
            response.setTotalParticipants(6);

            // 시간대별 상세 정보
            List<ScheduleResultResponse.TimeSlotDetail> timeSlotDetails = new ArrayList<>();

            // 첫 번째 시간대 (7월 금요일)
            ScheduleResultResponse.TimeSlotDetail slot1 = new ScheduleResultResponse.TimeSlotDetail();
            slot1.setStartTime(LocalDateTime.of(2025, 7, 11, 18, 0));
            slot1.setEndTime(LocalDateTime.of(2025, 7, 11, 22, 0));
            slot1.setParticipantCount(6);
            slot1.setIsSelected(false);
            slot1.setTimeSlotId("slot_1");

            // 첫 번째 시간대 참여자들
            List<ScheduleResultResponse.Participant> participants1 = new ArrayList<>();
            String[] names = {"박은서", "한예주", "박은규", "박상욱", "황수지", "배수지"};
            for (String name : names) {
                ScheduleResultResponse.Participant participant = new ScheduleResultResponse.Participant();
                participant.setMemberName(name);
                participants1.add(participant);
            }
            slot1.setParticipants(participants1);

            // 두 번째 시간대 (7월 토요일)
            ScheduleResultResponse.TimeSlotDetail slot2 = new ScheduleResultResponse.TimeSlotDetail();
            slot2.setStartTime(LocalDateTime.of(2025, 7, 12, 18, 0));
            slot2.setEndTime(LocalDateTime.of(2025, 7, 12, 22, 0));
            slot2.setParticipantCount(6);
            slot2.setIsSelected(false);
            slot2.setTimeSlotId("slot_2");
            slot2.setParticipants(new ArrayList<>(participants1)); // 동일한 참여자들

            // 세 번째 시간대 (7월 일요일)
            ScheduleResultResponse.TimeSlotDetail slot3 = new ScheduleResultResponse.TimeSlotDetail();
            slot3.setStartTime(LocalDateTime.of(2025, 7, 13, 18, 0));
            slot3.setEndTime(LocalDateTime.of(2025, 7, 13, 22, 0));
            slot3.setParticipantCount(6);
            slot3.setIsSelected(false);
            slot3.setTimeSlotId("slot_3");
            slot3.setParticipants(new ArrayList<>(participants1)); // 동일한 참여자들

            timeSlotDetails.add(slot1);
            timeSlotDetails.add(slot2);
            timeSlotDetails.add(slot3);

            // 추천 요약 정보
            ScheduleResultResponse.Recommendation recommendationSummary = new ScheduleResultResponse.Recommendation();
            recommendationSummary.setLongestMeetingTime(slot1);
            recommendationSummary.setEarliestMeetingTime(slot1);

            response.setRecommendation(recommendationSummary);

            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                return ResponseEntity.status(401).body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400).body(ApiResponse.error(ResponseCode.BAD_REQUEST, "eventId 값이 유효하지 않습니다."));
        }

    }

    // 이벤트 삭제
    @DeleteMapping("/{eventId}")
    public ResponseEntity<ApiResponse<EventDeleteResponse>> deleteEvent(@PathVariable Long eventId) {

        try {
            return ResponseEntity.ok(ApiResponse.success("이벤트가 성공적으로 삭제되었습니다."));
        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                return ResponseEntity.status(401).body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "권한이 없습니다."));
            }
            return ResponseEntity.status(400).body(ApiResponse.error(ResponseCode.BAD_REQUEST, "eventId 값이 유효하지 않습니다."));
        }
    }
}
