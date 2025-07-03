package com.grepp.spring.app.model.workspace.repos;

import com.grepp.spring.app.model.detail.domain.Detail;
import com.grepp.spring.app.model.workspace.domain.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    Workspace findFirstByDetail(Detail detail);

}
