package com.grepp.spring.calendar_detail.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CalendarDetailDTO {

    private Long calendarDetailId;

    @NotNull
    @Size(max = 255)
    private String title;

    @NotNull
    @Size(max = 255)
    private String startDatetime;

    @NotNull
    @Size(max = 255)
    private String endDatetime;

    @NotNull
    private LocalDateTime syncedAt;

    private Long calendar;

}
