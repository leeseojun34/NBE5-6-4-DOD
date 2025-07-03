package com.grepp.spring.app.model.event_user.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EventUserDTO {

    private Long eventUserId;

    @Size(max = 255)
    private String user;

    private Long event;

}
