package com.grepp.spring.app.model.meeting.repos;

import com.grepp.spring.app.model.meeting.domain.Meeting;
import com.grepp.spring.app.model.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    Meeting findFirstBySchedule(Schedule schedule);

    boolean existsByScheduleScheduleId(Long scheduleId);

}
