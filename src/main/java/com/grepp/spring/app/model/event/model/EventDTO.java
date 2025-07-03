package com.grepp.spring.app.model.event.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EventDTO {

    private Long eventId;

    @NotNull
    @Size(max = 255)
    private String title;

    private String description;

    @NotNull
    private Long creator;

    @NotNull
    @Size(max = 255)
    private String meetingType;

    @NotNull
    private Integer maxMember;

    private Long group;

}
