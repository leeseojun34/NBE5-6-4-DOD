package com.grepp.spring.app.controller.api.event.payload;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventUpdateResponse {
    private Long eventId;
    private String title;
    private String description;
    private String meetingType;
    private Integer maxMember;
    private String status;
    private LocalDateTime updatedAt;
    private String message;
}
