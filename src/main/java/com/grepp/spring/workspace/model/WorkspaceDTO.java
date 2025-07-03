package com.grepp.spring.workspace.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class WorkspaceDTO {

    private Long workspaceId;

    @NotNull
    @Size(max = 255)
    private String url;

    private Long detail;

}
