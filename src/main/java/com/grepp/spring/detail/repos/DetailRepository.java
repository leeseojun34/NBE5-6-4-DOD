package com.grepp.spring.detail.repos;

import com.grepp.spring.detail.domain.Detail;
import com.grepp.spring.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DetailRepository extends JpaRepository<Detail, Long> {

    Detail findFirstBySchedule(Schedule schedule);

    boolean existsByScheduleScheduleId(Long scheduleId);

}
