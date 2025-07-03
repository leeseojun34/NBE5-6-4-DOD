package com.grepp.spring.app.model.location.repos;

import com.grepp.spring.app.model.location.domain.Location;
import com.grepp.spring.app.model.middle_region.domain.MiddleRegion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findFirstByMiddleRegion(MiddleRegion middleRegion);

    boolean existsByMiddleRegionMiddleRegionId(Long middleRegionId);

}
