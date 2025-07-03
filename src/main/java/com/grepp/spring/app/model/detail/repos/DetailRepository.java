package com.grepp.spring.app.model.detail.repos;

import com.grepp.spring.app.model.detail.domain.Detail;
import com.grepp.spring.app.model.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DetailRepository extends JpaRepository<Detail, Long> {

    Detail findFirstBySchedule(Schedule schedule);

    boolean existsByScheduleScheduleId(Long scheduleId);

}
