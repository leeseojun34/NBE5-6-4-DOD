package com.grepp.spring.app.controller.api.mainpage.payload;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShowCalendarResponse {
  private List<CalendarSchedule> scheduleId;

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CalendarSchedule {
    private Long scheduleId;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private String category;
    private boolean isAllDay;
    private Long groupId;
    private String groupName;
  }



}
