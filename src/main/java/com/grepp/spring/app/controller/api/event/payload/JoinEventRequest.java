package com.grepp.spring.app.controller.api.event.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinEventRequest {
    private String memberId;
    private Long eventId;
}
