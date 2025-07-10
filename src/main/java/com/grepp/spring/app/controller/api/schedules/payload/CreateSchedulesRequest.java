package com.grepp.spring.app.controller.api.schedules.payload;

import com.grepp.spring.app.model.schedule.domain.ON_OFFLINE;
import com.grepp.spring.app.model.schedule.domain.SCHEDULES_STATUS;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSchedulesRequest {
    private Long eventId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private SCHEDULES_STATUS scheduleStatus;
    private ON_OFFLINE meetingType;

    private String scheduleName;        // 추가
    private String description;

    private int maxNumber; // 추가 // 이벤트 참여 인원 수

    // NOTE  : 추가
    private Map<String, String> memberRoles; // 멤버 별 권한 정리 (멤버ID , 권한)
}
