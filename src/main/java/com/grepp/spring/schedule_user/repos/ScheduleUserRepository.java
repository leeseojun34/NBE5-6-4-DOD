package com.grepp.spring.schedule_user.repos;

import com.grepp.spring.member.domain.Member;
import com.grepp.spring.schedule.domain.Schedule;
import com.grepp.spring.schedule_user.domain.ScheduleUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleUserRepository extends JpaRepository<ScheduleUser, Long> {

    ScheduleUser findFirstByUser(Member member);

    ScheduleUser findFirstBySchedule(Schedule schedule);

}
