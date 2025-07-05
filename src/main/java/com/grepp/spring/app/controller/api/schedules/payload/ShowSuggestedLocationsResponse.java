package com.grepp.spring.app.controller.api.schedules.payload;

import com.grepp.spring.app.model.middle_region.domain.MiddleRegion;
import com.grepp.spring.app.model.schedule.domain.VOTE_STATUS;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowSuggestedLocationsResponse {
    private List<MiddleRegion> middleRegions;
    private String locationName;
    private Double latitude;
    private	Double longitude;
    private	Long suggestedMemberId;
    private Long voteCount;
    private VOTE_STATUS SCHEDULES_STATUS;
}
