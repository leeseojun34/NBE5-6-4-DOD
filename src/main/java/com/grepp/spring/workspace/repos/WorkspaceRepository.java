package com.grepp.spring.workspace.repos;

import com.grepp.spring.detail.domain.Detail;
import com.grepp.spring.workspace.domain.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    Workspace findFirstByDetail(Detail detail);

}
