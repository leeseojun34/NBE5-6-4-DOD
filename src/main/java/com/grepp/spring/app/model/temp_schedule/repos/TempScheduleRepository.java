package com.grepp.spring.app.model.temp_schedule.repos;

import com.grepp.spring.app.model.event_member.domain.EventMember;
import com.grepp.spring.app.model.temp_schedule.domain.TempSchedule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TempScheduleRepository extends JpaRepository<TempSchedule, Long> {

    TempSchedule findFirstByEventMember(EventMember eventMember);

}
