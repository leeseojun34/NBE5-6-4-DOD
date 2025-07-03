package com.grepp.spring.app.model.depart_region.repos;

import com.grepp.spring.app.model.depart_region.domain.DepartRegion;
import com.grepp.spring.app.model.meeting.domain.Meeting;
import com.grepp.spring.app.model.middle_region.domain.MiddleRegion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartRegionRepository extends JpaRepository<DepartRegion, Long> {

    DepartRegion findFirstByMeeting(Meeting meeting);

    DepartRegion findFirstByMiddleRegion(MiddleRegion middleRegion);

}
