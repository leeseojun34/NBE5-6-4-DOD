package com.grepp.spring.app.controller.api.event.payload;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllTimeScheduleResponse {
    private Long eventId;
    private String eventTitle;
    private List<MemberSchedule> memberSchedules;
    private Integer totalMembers;
    private Integer confirmedMembers;

    @Getter
    @Setter
    public static class MemberSchedule {
        private Long eventMemberId;
        private String memberName;
        private String role;
        private List<DailyTimeSlot> dailyTimeSlots;
        private Boolean isConfirmed;
    }

    @Getter
    @Setter
    public static class DailyTimeSlot {
        private LocalDate date;
        private String dayOfWeek; // "MON", "TUE", "WED", etc.
        private String displayDate; // "07/13" 형태
        private Long timeBit; // 해당 멤버의 가능한 시간 비트
    }
}