package com.grepp.spring.group.repos;

import com.grepp.spring.group.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupRepository extends JpaRepository<Group, Long> {
}
