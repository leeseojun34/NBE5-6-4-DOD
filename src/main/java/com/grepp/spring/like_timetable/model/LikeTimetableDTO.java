package com.grepp.spring.like_timetable.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LikeTimetableDTO {

    private Long likeTimetableId;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @NotNull
    @Size(max = 255)
    private String weekday;

    @Size(max = 255)
    private String user;

}
