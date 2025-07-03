package com.grepp.spring.calendar.repos;

import com.grepp.spring.calendar.domain.Calendar;
import com.grepp.spring.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    Calendar findFirstByUser(Member member);

    boolean existsByUserUserIdIgnoreCase(String userId);

}
