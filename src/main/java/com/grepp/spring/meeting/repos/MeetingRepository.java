package com.grepp.spring.meeting.repos;

import com.grepp.spring.meeting.domain.Meeting;
import com.grepp.spring.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    Meeting findFirstBySchedule(Schedule schedule);

    boolean existsByScheduleScheduleId(Long scheduleId);

}
