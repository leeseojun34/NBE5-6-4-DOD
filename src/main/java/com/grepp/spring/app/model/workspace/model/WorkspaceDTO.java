package com.grepp.spring.app.model.workspace.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class WorkspaceDTO {

    private Long id;

    @NotNull
    private String url;

    private Long schedule;

}
