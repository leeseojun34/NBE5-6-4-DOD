package com.grepp.spring.app.model.candidate_date.repos;

import com.grepp.spring.app.model.candidate_date.domain.CandidateDate;
import com.grepp.spring.app.model.event.domain.Event;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CandidateDateRepository extends JpaRepository<CandidateDate, Long> {

    CandidateDate findFirstByEvent(Event event);

    boolean existsByDate(LocalDateTime date);

}
