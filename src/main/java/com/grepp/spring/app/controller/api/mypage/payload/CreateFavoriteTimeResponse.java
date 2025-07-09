package com.grepp.spring.app.controller.api.mypage.payload;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFavoriteTimeResponse {

  private List<FavoriteTimeList> fav_times;

  @Getter @Setter
  public static class FavoriteTimeList{
    private Long favoriteTimeId;
    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek weekday;
    private LocalDateTime createdAt;
  }

}
