package com.grepp.spring.app.model.calendar.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CalendarDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private Boolean synced;

    @NotNull
    private LocalDateTime syncedAt;

    @Size(max = 255)
    @CalendarMemberUnique
    private String member;

}
