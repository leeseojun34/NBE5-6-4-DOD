package com.grepp.spring.app.model.group.repos;

import com.grepp.spring.app.model.group.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupRepository extends JpaRepository<Group, Long> {
}
