package com.grepp.spring.candidate_date.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CandidateDateDTO {

    private Long candidateDateId;

    @NotNull
    @CandidateDateDateUnique
    private LocalDateTime date;

    private Long event;

}
