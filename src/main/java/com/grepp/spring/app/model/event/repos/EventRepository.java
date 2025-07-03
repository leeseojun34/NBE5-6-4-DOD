package com.grepp.spring.app.model.event.repos;

import com.grepp.spring.app.model.event.domain.Event;
import com.grepp.spring.app.model.group.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventRepository extends JpaRepository<Event, Long> {

    Event findFirstByGroup(Group group);

}
