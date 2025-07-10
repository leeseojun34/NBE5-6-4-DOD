package com.grepp.spring.app.controller.api.schedules.payload;

import com.grepp.spring.app.model.schedule.domain.MEETING_PLATFORM;
import com.grepp.spring.app.model.schedule.domain.ON_OFFLINE;
import com.grepp.spring.app.model.schedule.domain.SCHEDULES_STATUS;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ModifySchedulesRequest {
    private Long eventId; // 추가

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private SCHEDULES_STATUS SCHEDULES_STATUS;
//    private ON_OFFLINE onOffline; // 추가
    private String scheduleName;
    private String description;


    // 오프라인일 때 오프라인 관련 필드
    private String location; // 중간장소
    private String specificLocation; // 세부장소

    // 온라인일 때 온라인 회의장 관련 필드
    private MEETING_PLATFORM meetingPlatform;
    private String platformName;
    private String platformURL;

    // 온라인/오프라인 공통 워크 스페이스
    private Long workspaceId; // 추가
    private String workspaceName; // 추가
    private String workspaceUrl; // 추가
}
