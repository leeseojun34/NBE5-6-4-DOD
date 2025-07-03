package com.grepp.spring.candidate_date.repos;

import com.grepp.spring.candidate_date.domain.CandidateDate;
import com.grepp.spring.event.domain.Event;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CandidateDateRepository extends JpaRepository<CandidateDate, Long> {

    CandidateDate findFirstByEvent(Event event);

    boolean existsByDate(LocalDateTime date);

}
