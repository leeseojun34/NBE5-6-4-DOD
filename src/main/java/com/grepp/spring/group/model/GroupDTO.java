package com.grepp.spring.group.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GroupDTO {

    private Long groupId;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String description;

    @NotNull
    @JsonProperty("isGrouped")
    private Boolean isGrouped;

}
