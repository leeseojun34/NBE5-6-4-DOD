package com.grepp.spring.app.model.location_candidate.repos;

import com.grepp.spring.app.model.detail.domain.Detail;
import com.grepp.spring.app.model.location_candidate.domain.LocationCandidate;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LocationCandidateRepository extends JpaRepository<LocationCandidate, Long> {

    LocationCandidate findFirstByDetail(Detail detail);

    boolean existsByLocationNameIgnoreCase(String locationName);

}
