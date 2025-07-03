package com.grepp.spring.temp_schedule.repos;

import com.grepp.spring.event_user.domain.EventUser;
import com.grepp.spring.temp_schedule.domain.TempSchedule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TempScheduleRepository extends JpaRepository<TempSchedule, Long> {

    TempSchedule findFirstByEventUser(EventUser eventUser);

}
