package com.grepp.spring.calendar_detail.repos;

import com.grepp.spring.calendar.domain.Calendar;
import com.grepp.spring.calendar_detail.domain.CalendarDetail;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CalendarDetailRepository extends JpaRepository<CalendarDetail, Long> {

    CalendarDetail findFirstByCalendar(Calendar calendar);

}
