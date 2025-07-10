package com.grepp.spring.app.controller.api.schedules.payload;

import com.grepp.spring.app.model.schedule.domain.MEETING_PLATFORM;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOnlineMeetingRequest {
    private MEETING_PLATFORM meetingPlatform;  // 온라인 플렛폼 종류 -> GOOGLE_MEET | ZOOM | NONE
}
