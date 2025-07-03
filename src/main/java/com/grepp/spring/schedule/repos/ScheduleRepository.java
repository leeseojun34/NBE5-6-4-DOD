package com.grepp.spring.schedule.repos;

import com.grepp.spring.event.domain.Event;
import com.grepp.spring.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Schedule findFirstByEvent(Event event);

}
