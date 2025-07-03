package com.grepp.spring.app.model.detail.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DetailDTO {

    private Long detailId;

    @NotNull
    @Size(max = 255)
    private String location;

    @DetailScheduleUnique
    private Long schedule;

}
