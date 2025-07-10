package com.grepp.spring.app.controller.api.schedules.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDepartLocationRequest {
    private String departLocationName;
    private Double longitude;
    private Double latitude;
}
