package com.grepp.spring.app.controller.api.event.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventRequest {

    private String title;
    private String description;
    private String meetingType;
    private Integer maxMember;
    private Long groupId;
    private List<CandidateDateWeb> dateList;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CandidateDateWeb {
        private List<LocalDate> dates;
        private LocalTime startTime;
        private LocalTime endTime;
    }
}
