package com.grepp.spring.app.controller.api.mypage.payload;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyFavoriteTimeResponse {
  private Long favoriteTimeId;
  private LocalTime startTime;
  private LocalTime endTime;
  private LocalDateTime dateTime;
  private DayOfWeek weekday;
  private LocalDateTime updatedAt;

}
