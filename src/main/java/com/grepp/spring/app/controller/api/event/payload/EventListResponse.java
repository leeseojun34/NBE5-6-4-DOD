package com.grepp.spring.app.controller.api.event.payload;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventListResponse {
    private List<EventList> events;
    private Integer totalCount;

    @Getter
    @Setter
    public static class EventList {
        private Long eventId;
        private Long groupId;
        private String title;
        private String description;
        private String meetingType;
        private Integer maxMember;
        private Integer currentMember;
        private LocalDateTime createdAt;
        private Boolean isGroupEvent;
        private String groupName;
    }
}
