package com.grepp.spring.app.controller.api.schedules.payload;

import com.grepp.spring.app.model.schedule.domain.MEETING_PLATFORM;
import com.grepp.spring.app.model.schedule.domain.SCHEDULES_STATUS;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowSchedulesResponse {
    private Long eventId; // 추가

    private LocalDateTime startTime;
    private LocalDateTime endTime;
//    private SCHEDULES_STATUS SCHEDULES_STATUS; // RECOMMEND | FIXED | NONE | COMPLETE
    private String location; // 중간장소
    private String specificLocation; // 상세장소
    private String description;
    private MEETING_PLATFORM meetingPlatform;
    private String platformUrl;
    private String scheduleName;

    private List<String> members; // 추가
    private Map<String, String> workspaces; // (workspacesName , workspacesUrl)

}
