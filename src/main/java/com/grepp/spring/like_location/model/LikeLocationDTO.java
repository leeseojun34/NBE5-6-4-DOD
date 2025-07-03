package com.grepp.spring.like_location.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LikeLocationDTO {

    private Long likeLocationId;

    @NotNull
    private Double longitude;

    @NotNull
    private Double latitude;

    @NotNull
    @Size(max = 255)
    private String locationName;

    @Size(max = 255)
    @LikeLocationUserUnique
    private String user;

}
