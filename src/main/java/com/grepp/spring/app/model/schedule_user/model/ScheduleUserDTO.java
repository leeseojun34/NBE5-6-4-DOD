package com.grepp.spring.app.model.schedule_user.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ScheduleUserDTO {

    private Long scheduleUserId;

    @NotNull
    @Size(max = 255)
    private String role;

    @Size(max = 255)
    private String user;

    private Long schedule;

}
