package com.grepp.spring.app.model.schedule_member.repos;

import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.middle_region.domain.MiddleRegion;
import com.grepp.spring.app.model.schedule.domain.Schedule;
import com.grepp.spring.app.model.schedule_member.domain.ScheduleMember;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleMemberRepository extends JpaRepository<ScheduleMember, Long> {

    ScheduleMember findFirstByMember(Member member);

    ScheduleMember findFirstBySchedule(Schedule schedule);

    ScheduleMember findFirstByMiddleRegion(MiddleRegion middleRegion);

}
