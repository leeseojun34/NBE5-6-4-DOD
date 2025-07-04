package com.grepp.spring.app.model.calendar_detail.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CalendarDetailDTO {

    private Long id;

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

    @NotNull
    @JsonProperty("isAllDay")
    private Boolean isAllDay;

    @NotNull
    private String externalEtag;

    private Long calendar;

}
