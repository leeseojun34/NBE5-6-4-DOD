package com.grepp.spring.app.model.schedule.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ScheduleDTO {

    private Long id;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @NotNull
    @Size(max = 255)
    private String status;

    @Size(max = 255)
    private String location;

    private String description;

    @Size(max = 255)
    private String meetingPlatform;

    private String platformUrl;

    @Size(max = 255)
    private String specificLocation;

    private Long event;

}
