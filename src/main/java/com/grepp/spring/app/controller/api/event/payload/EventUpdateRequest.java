package com.grepp.spring.app.controller.api.event.payload;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventUpdateRequest {

    @NotNull
    private String title;

    private String description;

    @NotNull
    private String meetingType; // ONLINE, OFFLINE

    @NotNull
    private Integer maxMember;

    private List<CandidateDateTime> candidateDates;

    private String location;
    private String specificLocation;

    @Getter
    @Setter
    public static class CandidateDateTime {
        @NotNull
        private LocalDateTime startTime;

        @NotNull
        private LocalDateTime endTime;
    }
}
