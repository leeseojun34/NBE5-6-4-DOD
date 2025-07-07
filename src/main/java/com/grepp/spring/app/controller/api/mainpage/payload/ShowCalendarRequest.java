package com.grepp.spring.app.controller.api.mainpage.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowCalendarRequest {
  private String memberId;
  private Long calendarId;
  private String yearMonth; // "year-month" 형태로 나오게끔, 서비스 단에서 변환해서 사용할 예정

}
