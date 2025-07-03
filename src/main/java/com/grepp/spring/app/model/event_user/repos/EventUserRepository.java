package com.grepp.spring.app.model.event_user.repos;

import com.grepp.spring.app.model.event.domain.Event;
import com.grepp.spring.app.model.event_user.domain.EventUser;
import com.grepp.spring.app.model.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventUserRepository extends JpaRepository<EventUser, Long> {

    EventUser findFirstByUser(Member member);

    EventUser findFirstByEvent(Event event);

}
