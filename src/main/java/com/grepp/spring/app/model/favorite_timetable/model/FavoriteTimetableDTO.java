package com.grepp.spring.app.model.favorite_timetable.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FavoriteTimetableDTO {

    private Long id;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @NotNull
    @Size(max = 255)
    private String weekday;

    @Size(max = 255)
    private String member;

}
