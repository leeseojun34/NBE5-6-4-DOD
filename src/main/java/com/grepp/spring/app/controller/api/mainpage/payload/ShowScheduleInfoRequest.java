package com.grepp.spring.app.controller.api.mainpage.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowScheduleInfoRequest {
  private Long memberId;
  private String scheduleId;

}
