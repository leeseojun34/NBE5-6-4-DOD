package com.grepp.spring.app.controller.api.schedules.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowMiddleLocationResponse {
    private String locationName;
    private Double latitude;
    private	Double longitude;
    private	Long voteCount;
}
