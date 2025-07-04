package com.grepp.spring.app.model.workspace.repos;

import com.grepp.spring.app.model.schedule.domain.Schedule;
import com.grepp.spring.app.model.workspace.domain.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    Workspace findFirstBySchedule(Schedule schedule);

}
