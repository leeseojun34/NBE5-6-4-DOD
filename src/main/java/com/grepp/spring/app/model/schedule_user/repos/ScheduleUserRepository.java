package com.grepp.spring.app.model.schedule_user.repos;

import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.schedule.domain.Schedule;
import com.grepp.spring.app.model.schedule_user.domain.ScheduleUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleUserRepository extends JpaRepository<ScheduleUser, Long> {

    ScheduleUser findFirstByUser(Member member);

    ScheduleUser findFirstBySchedule(Schedule schedule);

}
