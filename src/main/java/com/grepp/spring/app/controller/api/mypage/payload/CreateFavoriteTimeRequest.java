package com.grepp.spring.app.controller.api.mypage.payload;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFavoriteTimeRequest {
    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek weekday;


}
