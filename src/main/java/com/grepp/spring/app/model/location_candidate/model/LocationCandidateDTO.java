package com.grepp.spring.app.model.location_candidate.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LocationCandidateDTO {

    private Long locationCandidateId;

    @NotNull
    @Size(max = 255)
    private String suggestUserId;

    @NotNull
    @Size(max = 255)
    @LocationCandidateLocationNameUnique
    private String locationName;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    private Integer voteCount;

    @NotNull
    @Size(max = 255)
    private String status;

    private Long detail;

}
