package com.grepp.spring.app.controller.api.mainpage.payload;

import com.grepp.spring.app.model.schedule.domain.SCHEDULES_STATUS;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowScheduleListResponse {
  private Long scheduleId;
  private String name; // 일정 이름
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private SCHEDULES_STATUS SCHEDULES_STATUS;
  private String description; // 일정에 대한 상세 설명, 내용
  private String location;
  private String category;

}
