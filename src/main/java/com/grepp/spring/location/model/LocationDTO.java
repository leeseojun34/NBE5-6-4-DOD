package com.grepp.spring.location.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LocationDTO {

    private Long locationId;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    @Size(max = 255)
    private String locationName;

    @LocationMiddleRegionUnique
    private Long middleRegion;

}
