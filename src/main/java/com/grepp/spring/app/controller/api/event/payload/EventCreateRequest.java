package com.grepp.spring.app.controller.api.event.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventCreateRequest {

    private Long groupId;
    private String creatorId;
    private String title;
    private String description;
    private String type;
    private Integer maxMember;
}
