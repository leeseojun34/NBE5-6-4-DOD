package com.grepp.spring.app.model.calendar.repos;

import com.grepp.spring.app.model.calendar.domain.Calendar;
import com.grepp.spring.app.model.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    Calendar findFirstByMember(Member member);

    boolean existsByMemberIdIgnoreCase(String id);

}
