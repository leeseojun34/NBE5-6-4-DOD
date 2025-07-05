package com.grepp.spring.app.controller.api.event.payload;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleResultResponse {
    private Long eventId;
    private String eventTitle;
    private Integer totalParticipants;
    private String meetingType;
    private String location;
    private String specificLocation;
    private String description;
    private String platformUrl;
    private String status; // FIXED
    private List<TimeSlotDetail> timeSlotDetails;
    private RecommendationSummary recommendationSummary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Getter
    @Setter
    public static class TimeSlotDetail {
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String displayTime;
        private Integer participantCount;
        private List<Participant> participants;
        private Boolean isRecommended;
        private Boolean isSelected; // 최종 선택된 시간대인지
        private String timeSlotId; // 시간대 식별자
    }

    @Getter
    @Setter
    public static class Participant {
        private String memberName;
        private String role;
        private Boolean isConfirmed;
        private String availabilityStatus; // AVAILABLE, UNAVAILABLE, PENDING
    }

    @Getter
    @Setter
    public static class RecommendationSummary {
        private TimeSlotDetail longestMeetingTime; // 가장 오래 만날 수 있는 시간
        private TimeSlotDetail earliestMeetingTime; // 가장 빨리 만날 수 있는 시간
    }
}
