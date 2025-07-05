package com.grepp.spring.app.controller.api.mypage.payload;

import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFavoriteTimeResponse {
  private Long favoriteTimeId;
  private LocalTime startTime;
  private LocalTime endTime;
  private LocalDateTime weekday;
  private LocalDateTime createdAt;
}
