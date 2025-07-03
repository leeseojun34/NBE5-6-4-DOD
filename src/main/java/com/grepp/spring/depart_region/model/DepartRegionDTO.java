package com.grepp.spring.depart_region.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DepartRegionDTO {

    private Long departRegionId;

    @NotNull
    @Size(max = 255)
    private String dapartLocationName;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    private Long meeting;

    private Long middleRegion;

}
