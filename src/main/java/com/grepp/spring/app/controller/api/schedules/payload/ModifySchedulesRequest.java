package com.grepp.spring.app.controller.api.schedules.payload;

import com.grepp.spring.app.model.schedule.domain.MEETING_PLATFORM;
import com.grepp.spring.app.model.schedule.domain.SCHEDULES_STATUS;
import java.time.LocalDateTime;

public class ModifySchedulesRequest {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private SCHEDULES_STATUS SCHEDULES_STATUS;
    private String location;
    private String specificLocation;
    private String description;
    private MEETING_PLATFORM meetingPlatform;
    private String platformURL;

}
