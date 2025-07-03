package com.grepp.spring.workspace.service;

import com.grepp.spring.detail.domain.Detail;
import com.grepp.spring.detail.repos.DetailRepository;
import com.grepp.spring.util.NotFoundException;
import com.grepp.spring.workspace.domain.Workspace;
import com.grepp.spring.workspace.model.WorkspaceDTO;
import com.grepp.spring.workspace.repos.WorkspaceRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final DetailRepository detailRepository;

    public WorkspaceService(final WorkspaceRepository workspaceRepository,
            final DetailRepository detailRepository) {
        this.workspaceRepository = workspaceRepository;
        this.detailRepository = detailRepository;
    }

    public List<WorkspaceDTO> findAll() {
        final List<Workspace> workspaces = workspaceRepository.findAll(Sort.by("workspaceId"));
        return workspaces.stream()
                .map(workspace -> mapToDTO(workspace, new WorkspaceDTO()))
                .toList();
    }

    public WorkspaceDTO get(final Long workspaceId) {
        return workspaceRepository.findById(workspaceId)
                .map(workspace -> mapToDTO(workspace, new WorkspaceDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final WorkspaceDTO workspaceDTO) {
        final Workspace workspace = new Workspace();
        mapToEntity(workspaceDTO, workspace);
        return workspaceRepository.save(workspace).getWorkspaceId();
    }

    public void update(final Long workspaceId, final WorkspaceDTO workspaceDTO) {
        final Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(workspaceDTO, workspace);
        workspaceRepository.save(workspace);
    }

    public void delete(final Long workspaceId) {
        workspaceRepository.deleteById(workspaceId);
    }

    private WorkspaceDTO mapToDTO(final Workspace workspace, final WorkspaceDTO workspaceDTO) {
        workspaceDTO.setWorkspaceId(workspace.getWorkspaceId());
        workspaceDTO.setUrl(workspace.getUrl());
        workspaceDTO.setDetail(workspace.getDetail() == null ? null : workspace.getDetail().getDetailId());
        return workspaceDTO;
    }

    private Workspace mapToEntity(final WorkspaceDTO workspaceDTO, final Workspace workspace) {
        workspace.setUrl(workspaceDTO.getUrl());
        final Detail detail = workspaceDTO.getDetail() == null ? null : detailRepository.findById(workspaceDTO.getDetail())
                .orElseThrow(() -> new NotFoundException("detail not found"));
        workspace.setDetail(detail);
        return workspace;
    }

}
