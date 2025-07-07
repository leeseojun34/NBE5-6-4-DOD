package com.grepp.spring.app.controller.api.mainpage.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ShowScheduleListRequest {
  private Long calendarId;

}
