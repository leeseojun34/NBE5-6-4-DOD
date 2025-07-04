package com.grepp.spring.app.model.schedule_member.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ScheduleMemberDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String role;

    @Size(max = 255)
    private String departLocationName;

    private Double latitude;

    private Double longitude;

    @Size(max = 255)
    private String member;

    private Long schedule;

    private Long middleRegion;

}
