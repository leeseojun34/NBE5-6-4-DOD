package com.grepp.spring.app.model.schedule.repos;

import com.grepp.spring.app.model.event.domain.Event;
import com.grepp.spring.app.model.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Schedule findFirstByEvent(Event event);

}
