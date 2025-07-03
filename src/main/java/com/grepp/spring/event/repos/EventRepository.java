package com.grepp.spring.event.repos;

import com.grepp.spring.event.domain.Event;
import com.grepp.spring.group.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventRepository extends JpaRepository<Event, Long> {

    Event findFirstByGroup(Group group);

}
