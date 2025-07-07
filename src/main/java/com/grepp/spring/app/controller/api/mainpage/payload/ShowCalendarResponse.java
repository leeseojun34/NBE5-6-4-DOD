package com.grepp.spring.app.controller.api.mainpage.payload;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowCalendarResponse {
  private String message;
  private List<CalendarSchedule> googleSchedules;
  private List<CalendarSchedule> internalSchedules;

  @Getter
  @Setter
  public static class CalendarSchedule {
    private Long scheduleId;
    private String memberId;
    private Long calendarId;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
//    private String category;
//    private boolean isAllDay;
  }


}
