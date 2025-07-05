package com.grepp.spring.app.controller.api.event.payload;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventCreateRequest {

    @NotNull
    private String title;
    private String description;
    @NotNull
    private List<DateList> dateList;
    @NotNull
    private String type;
    @NotNull
    private Integer maxMember;

    @Getter
    @Setter
    public static class DateList {
        private String date;
        @NotNull
        private String startTime;
        @NotNull
        private String endTime;

        public DateList(String date, String startTime, String endTime) {
            this.date = date;
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }
}
