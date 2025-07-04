package com.grepp.spring.app.model.favorite_location.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FavoriteLocationDTO {

    private Long id;

    @NotNull
    private Double longitude;

    @NotNull
    private Double latitude;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    @FavoriteLocationMemberUnique
    private String member;

}
