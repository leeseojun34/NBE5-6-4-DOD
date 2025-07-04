package com.grepp.spring.app.model.candidate_date.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CandidateDateDTO {

    private Long id;

    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    private Long event;

}
