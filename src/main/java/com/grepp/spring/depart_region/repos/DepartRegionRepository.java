package com.grepp.spring.depart_region.repos;

import com.grepp.spring.depart_region.domain.DepartRegion;
import com.grepp.spring.meeting.domain.Meeting;
import com.grepp.spring.middle_region.domain.MiddleRegion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartRegionRepository extends JpaRepository<DepartRegion, Long> {

    DepartRegion findFirstByMeeting(Meeting meeting);

    DepartRegion findFirstByMiddleRegion(MiddleRegion middleRegion);

}
