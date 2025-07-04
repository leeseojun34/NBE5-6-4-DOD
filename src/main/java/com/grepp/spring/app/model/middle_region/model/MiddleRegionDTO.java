package com.grepp.spring.app.model.middle_region.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MiddleRegionDTO {

    private Long id;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

}
