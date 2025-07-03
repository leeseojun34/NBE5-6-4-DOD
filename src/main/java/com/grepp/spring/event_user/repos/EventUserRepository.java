package com.grepp.spring.event_user.repos;

import com.grepp.spring.event.domain.Event;
import com.grepp.spring.event_user.domain.EventUser;
import com.grepp.spring.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventUserRepository extends JpaRepository<EventUser, Long> {

    EventUser findFirstByUser(Member member);

    EventUser findFirstByEvent(Event event);

}
