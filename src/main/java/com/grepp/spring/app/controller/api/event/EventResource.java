package com.grepp.spring.app.controller.api.event;

import com.grepp.spring.app.controller.api.event.payload.*;
import com.grepp.spring.app.model.event.service.EventService;
import com.grepp.spring.app.model.group.repos.GroupRepository;
import com.grepp.spring.infra.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/events", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventResource {

    private final EventService eventService;
    private final GroupRepository groupRepository;

    public EventResource(final EventService eventService, final GroupRepository groupRepository) {
        this.eventService = eventService;
        this.groupRepository = groupRepository;
    }

    // 이벤트 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<EventListResponse>> getEventList(@RequestParam String memberId) {
        EventListResponse response = new EventListResponse();
        response.setTotalCount(2);
        List<EventListResponse.EventList> events = new ArrayList<>();

        EventListResponse.EventList event1 = new EventListResponse.EventList();
        event1.setEventId(1L);
        event1.setTitle("팀 미팅 일정 조율 1");
        event1.setDescription("Q3 계획 수립을 위한 팀 미팅 1");
        event1.setMeetingType("ONLINE");
        event1.setMaxMember(5);
        event1.setCurrentMember(3);
        event1.setCreatedAt(LocalDateTime.of(2025, 7, 5, 9, 0));
        event1.setIsGroupEvent(true);
        event1.setGroupName("개발팀");

        EventListResponse.EventList event2 = new EventListResponse.EventList();
        event2.setEventId(2L);
        event2.setTitle("팀 미팅 일정 조율 2");
        event2.setDescription("Q3 계획 수립을 위한 팀 미팅 2");
        event2.setMeetingType("OFFLINE");
        event2.setMaxMember(10);
        event2.setCurrentMember(7);
        event2.setCreatedAt(LocalDateTime.of(2025, 7, 8, 16, 30));
        event2.setIsGroupEvent(true);
        event2.setGroupName("개발팀");

        events.add(event1);
        events.add(event2);

        response.setEvents(events);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 이벤트 생성
    @PostMapping
    public ResponseEntity<ApiResponse<EventCreateResponse>> createEvent(@RequestBody @Valid EventCreateRequest request) {
        EventCreateResponse response = new EventCreateResponse();
        response.setEventId(1L);
        response.setTitle("Sample Event");
        response.setShareLink("http://example.com/event/1");
        response.setType("Offline");
        response.setCreatedAt(LocalDateTime.now());

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 이벤트 일정 참여
    @PostMapping("/{eventId}")
    public ResponseEntity<ApiResponse<JoinEventResponse>> joinEvent(
        @PathVariable Long eventId,
        @RequestBody JoinEventRequest request) {

        JoinEventResponse response = new JoinEventResponse();
        response.setMemberId(1L);
        response.setRole("MEMBER");
        response.setMemberName("김철수");
        response.setJoinedAt(LocalDateTime.now());

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 개인의 가능한 시간대 생성/수정
    @PostMapping("/{eventId}/my-time")
    public ResponseEntity<ApiResponse<MyTimeScheduleResponse>> createOrUpdateMyTime(
        @PathVariable Long eventId,
        @RequestBody @Valid MyTimeScheduleRequest request) {

        MyTimeScheduleResponse response = new MyTimeScheduleResponse();
        response.setEventMemberId(1001L);
        response.setMemberName("김철수");
        response.setIsConfirmed(false);
        response.setCreatedAt(LocalDateTime.now());

        List<MyTimeScheduleResponse.DailyTimeSlot> dailyTimeSlots = new ArrayList<>();

        for (MyTimeScheduleRequest.DailyTimeSlot requestSlot : request.getDailyTimeSlots()) {
            MyTimeScheduleResponse.DailyTimeSlot responseSlot = new MyTimeScheduleResponse.DailyTimeSlot();
            responseSlot.setDate(requestSlot.getDate());
            responseSlot.setDayOfWeek(requestSlot.getDate().getDayOfWeek().toString().substring(0, 3).toUpperCase());
            responseSlot.setDisplayDate(String.format("%02d/%02d",
                requestSlot.getDate().getMonthValue(), requestSlot.getDate().getDayOfMonth()));
            responseSlot.setTimeBit(requestSlot.getTimeBit());

            // 비트마스크를 TimeSlotInfo 리스트로 변환 (프론트 편의용)
            List<MyTimeScheduleResponse.TimeSlotInfo> timeSlotInfos = new ArrayList<>();
            for (int i = 0; i < 48; i++) { // 0~47 (24시간 * 2)
                MyTimeScheduleResponse.TimeSlotInfo info = new MyTimeScheduleResponse.TimeSlotInfo();
                info.setSlotIndex(i);
                info.setTimeLabel(String.format("%02d:%02d", i / 2, (i % 2) * 30));
                info.setIsSelected((requestSlot.getTimeBit() & (1L << i)) != 0);
                timeSlotInfos.add(info);
            }
            responseSlot.setTimeSlotInfos(timeSlotInfos);

            dailyTimeSlots.add(responseSlot);
        }
        response.setDailyTimeSlots(dailyTimeSlots);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 참여자 전원의 가능한 시간대 조회
    @GetMapping("/{eventId}/all-time")
    public ResponseEntity<ApiResponse<AllTimeScheduleResponse>> getAllTimeSchedules(@PathVariable Long eventId) {
        // TODO: 구현 로직 고민중
        return null;
    }

    // 개인의 가능한 시간대 확정
    @PostMapping("/{eventId}/complete")
    public ResponseEntity<ApiResponse<CompleteMyTimeResponse>> completeMyTime(@PathVariable Long eventId) {

        CompleteMyTimeResponse response = new CompleteMyTimeResponse();
        response.setEventId(1L);
        response.setEventMemberId(1001L);
        response.setIsConfirmed(true);
        response.setConfirmedAt(LocalDateTime.now());

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 이벤트 조율 결과 생성
    @PostMapping("/events/{eventId}/result")
    public ResponseEntity<ApiResponse<CreateScheduleResultResponse>> createScheduleResult(
        @PathVariable Long eventId,
        @RequestBody @Valid CreateScheduleResultRequest request) {

        CreateScheduleResultResponse response = new CreateScheduleResultResponse();
        response.setEventId(eventId);
        response.setEventTitle("카츠오모이 가는날");
        response.setTotalParticipants(6);
        response.setStatus("ANALYZING");
        response.setCreatedAt(LocalDateTime.now());

        // 시간대별 조율 결과
        List<CreateScheduleResultResponse.TimeSlotSummary> timeSlots = new ArrayList<>();

        // 첫 번째 시간대
        CreateScheduleResultResponse.TimeSlotSummary slot1 = new CreateScheduleResultResponse.TimeSlotSummary();
        slot1.setStartTime(LocalDateTime.of(2025, 7, 11, 18, 0));
        slot1.setEndTime(LocalDateTime.of(2025, 7, 11, 22, 0));
        slot1.setParticipantCount(6);
        slot1.setParticipantNames(Arrays.asList("박은서", "한예주", "박은규", "박상욱", "황수지", "배수지"));
        slot1.setDisplayTime("2025년 7월 10일 (금) 18:00 ~22:00");

        // 두 번째 시간대
        CreateScheduleResultResponse.TimeSlotSummary slot2 = new CreateScheduleResultResponse.TimeSlotSummary();
        slot2.setStartTime(LocalDateTime.of(2025, 7, 12, 18, 0));
        slot2.setEndTime(LocalDateTime.of(2025, 7, 12, 22, 0));
        slot2.setParticipantCount(6);
        slot2.setParticipantNames(Arrays.asList("박은서", "한예주", "박은규", "박상욱", "황수지", "배수지"));
        slot2.setDisplayTime("2025년 7월 14일 (토) 18:00 ~22:00");

        // 세 번째 시간대
        CreateScheduleResultResponse.TimeSlotSummary slot3 = new CreateScheduleResultResponse.TimeSlotSummary();
        slot3.setStartTime(LocalDateTime.of(2025, 7, 13, 18, 0));
        slot3.setEndTime(LocalDateTime.of(2025, 7, 13, 22, 0));
        slot3.setParticipantCount(6);
        slot3.setParticipantNames(Arrays.asList("박은서", "한예주", "박은규", "박상욱", "황수지", "배수지"));
        slot3.setDisplayTime("2025년 7월 15일 (일) 18:00 ~22:00");

        timeSlots.add(slot1);
        timeSlots.add(slot2);
        timeSlots.add(slot3);
        response.setTimeSlotSummaries(timeSlots);

        // 추천 시간대 정보
        CreateScheduleResultResponse.RecommendedSlots recommendedSlots = new CreateScheduleResultResponse.RecommendedSlots();
        recommendedSlots.setLongestMeetingTime(slot1); // 가장 오래 만날 수 있는 시간
        recommendedSlots.setEarliestMeetingTime(slot1); // 가장 빨리 만날 수 있는 시간

        response.setRecommendedSlots(recommendedSlots);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 이벤트 조율 결과 조회
    @GetMapping("/events/{eventId}/result")
    public ResponseEntity<ApiResponse<ScheduleResultResponse>> getScheduleResult(@PathVariable Long eventId) {

        ScheduleResultResponse response = new ScheduleResultResponse();
        response.setEventId(eventId);
        response.setEventTitle("카츠오모이 가는날");
        response.setTotalParticipants(6);
        response.setMeetingType("OFFLINE");
        response.setLocation("강남구");
        response.setSpecificLocation("카츠오모이 강남점");
        response.setDescription("6명의 시간 조율 결과");
        response.setStatus("ANALYZING");
        response.setCreatedAt(LocalDateTime.now());
        response.setUpdatedAt(LocalDateTime.now());

        // 시간대별 상세 정보
        List<ScheduleResultResponse.TimeSlotDetail> timeSlotDetails = new ArrayList<>();

        // 첫 번째 시간대 (7월 금요일)
        ScheduleResultResponse.TimeSlotDetail slot1 = new ScheduleResultResponse.TimeSlotDetail();
        slot1.setStartTime(LocalDateTime.of(2025, 7, 11, 18, 0));
        slot1.setEndTime(LocalDateTime.of(2025, 7, 11, 22, 0));
        slot1.setDisplayTime("7월 (금) 18:00 ~22:00");
        slot1.setParticipantCount(6);
        slot1.setIsRecommended(true);
        slot1.setIsSelected(false);
        slot1.setTimeSlotId("slot_1");

        // 첫 번째 시간대 참여자들
        List<ScheduleResultResponse.Participant> participants1 = new ArrayList<>();
        String[] names = {"박은서", "한예주", "박은규", "박상욱", "황수지", "배수지"};
        for (String name : names) {
            ScheduleResultResponse.Participant participant = new ScheduleResultResponse.Participant();
            participant.setMemberName(name);
            participant.setRole("MEMBER");
            participant.setIsConfirmed(true);
            participant.setAvailabilityStatus("AVAILABLE");
            participants1.add(participant);
        }
        slot1.setParticipants(participants1);

        // 두 번째 시간대 (7월 토요일)
        ScheduleResultResponse.TimeSlotDetail slot2 = new ScheduleResultResponse.TimeSlotDetail();
        slot2.setStartTime(LocalDateTime.of(2025, 7, 12, 18, 0));
        slot2.setEndTime(LocalDateTime.of(2025, 7, 12, 22, 0));
        slot2.setDisplayTime("7월 (토) 18:00 ~22:00");
        slot2.setParticipantCount(6);
        slot2.setIsRecommended(false);
        slot2.setIsSelected(false);
        slot2.setTimeSlotId("slot_2");
        slot2.setParticipants(new ArrayList<>(participants1)); // 동일한 참여자들

        // 세 번째 시간대 (7월 일요일)
        ScheduleResultResponse.TimeSlotDetail slot3 = new ScheduleResultResponse.TimeSlotDetail();
        slot3.setStartTime(LocalDateTime.of(2025, 7, 13, 18, 0));
        slot3.setEndTime(LocalDateTime.of(2025, 7, 13, 22, 0));
        slot3.setDisplayTime("7월 (일) 18:00 ~22:00");
        slot3.setParticipantCount(6);
        slot3.setIsRecommended(false);
        slot3.setIsSelected(false);
        slot3.setTimeSlotId("slot_3");
        slot3.setParticipants(new ArrayList<>(participants1)); // 동일한 참여자들

        timeSlotDetails.add(slot1);
        timeSlotDetails.add(slot2);
        timeSlotDetails.add(slot3);
        response.setTimeSlotDetails(timeSlotDetails);

        // 추천 요약 정보
        ScheduleResultResponse.RecommendationSummary recommendationSummary = new ScheduleResultResponse.RecommendationSummary();
        recommendationSummary.setLongestMeetingTime(slot1);
        recommendationSummary.setEarliestMeetingTime(slot1);

        response.setRecommendationSummary(recommendationSummary);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 이벤트 수정
    @PutMapping("/{eventId}")
    public ResponseEntity<ApiResponse<EventUpdateResponse>> updateEvent(
        @PathVariable Long eventId,
        @RequestBody @Valid EventUpdateRequest request) {

        EventUpdateResponse response = new EventUpdateResponse();
        response.setEventId(eventId);
        response.setTitle(request.getTitle());
        response.setDescription(request.getDescription());
        response.setMeetingType(request.getMeetingType());
        response.setMaxMember(request.getMaxMember());
        response.setStatus("UPDATED");
        response.setUpdatedAt(LocalDateTime.now());
        response.setMessage("이벤트가 성공적으로 수정되었습니다.");

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 이벤트 삭제
    @DeleteMapping("/{eventId}")
    public ResponseEntity<ApiResponse<EventDeleteResponse>> deleteEvent(@PathVariable Long eventId) {

        EventDeleteResponse response = new EventDeleteResponse();
        response.setEventId(eventId);
        response.setDeletedAt(LocalDateTime.now());

        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
