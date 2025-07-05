package com.grepp.spring.app.controller.api.event.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompleteMyTimeRequest {
    private Long eventId;
    private Long eventMemberId;
}