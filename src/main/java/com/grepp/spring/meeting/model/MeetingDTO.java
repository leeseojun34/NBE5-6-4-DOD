package com.grepp.spring.meeting.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MeetingDTO {

    private Long meetingId;

    @NotNull
    @Size(max = 255)
    private String meetingPlatform;

    @NotNull
    @Size(max = 255)
    private String platformUrl;

    @MeetingScheduleUnique
    private Long schedule;

}
