package com.grepp.spring.app.model.location.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LocationDTO {

    private Long id;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String suggestedMemberId;

    private Long voteCount;

    @NotNull
    @Size(max = 255)
    private String status;

    private Long middleRegion;

}
