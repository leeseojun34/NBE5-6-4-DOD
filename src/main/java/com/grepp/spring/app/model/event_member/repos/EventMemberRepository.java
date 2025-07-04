package com.grepp.spring.app.model.event_member.repos;

import com.grepp.spring.app.model.event.domain.Event;
import com.grepp.spring.app.model.event_member.domain.EventMember;
import com.grepp.spring.app.model.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventMemberRepository extends JpaRepository<EventMember, Long> {

    EventMember findFirstByMember(Member member);

    EventMember findFirstByEvent(Event event);

}
