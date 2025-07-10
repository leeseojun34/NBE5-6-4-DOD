package com.grepp.spring.app.controller.api.schedules.payload;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowMiddleLocationResponse {
    private String locationName;
    private Double latitude;
    private	Double longitude;
//    private	Long voteCount;

    private Map<String, String> metroLines;  // (호선, 호선 색)
}
