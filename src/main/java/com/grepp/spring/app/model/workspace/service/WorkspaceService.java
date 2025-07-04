package com.grepp.spring.app.model.workspace.service;

import com.grepp.spring.app.model.schedule.domain.Schedule;
import com.grepp.spring.app.model.schedule.repos.ScheduleRepository;
import com.grepp.spring.util.NotFoundException;
import com.grepp.spring.app.model.workspace.domain.Workspace;
import com.grepp.spring.app.model.workspace.model.WorkspaceDTO;
import com.grepp.spring.app.model.workspace.repos.WorkspaceRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final ScheduleRepository scheduleRepository;

    public WorkspaceService(final WorkspaceRepository workspaceRepository,
            final ScheduleRepository scheduleRepository) {
        this.workspaceRepository = workspaceRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public List<WorkspaceDTO> findAll() {
        final List<Workspace> workspaces = workspaceRepository.findAll(Sort.by("id"));
        return workspaces.stream()
                .map(workspace -> mapToDTO(workspace, new WorkspaceDTO()))
                .toList();
    }

    public WorkspaceDTO get(final Long id) {
        return workspaceRepository.findById(id)
                .map(workspace -> mapToDTO(workspace, new WorkspaceDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final WorkspaceDTO workspaceDTO) {
        final Workspace workspace = new Workspace();
        mapToEntity(workspaceDTO, workspace);
        return workspaceRepository.save(workspace).getId();
    }

    public void update(final Long id, final WorkspaceDTO workspaceDTO) {
        final Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(workspaceDTO, workspace);
        workspaceRepository.save(workspace);
    }

    public void delete(final Long id) {
        workspaceRepository.deleteById(id);
    }

    private WorkspaceDTO mapToDTO(final Workspace workspace, final WorkspaceDTO workspaceDTO) {
        workspaceDTO.setId(workspace.getId());
        workspaceDTO.setUrl(workspace.getUrl());
        workspaceDTO.setSchedule(workspace.getSchedule() == null ? null : workspace.getSchedule().getId());
        return workspaceDTO;
    }

    private Workspace mapToEntity(final WorkspaceDTO workspaceDTO, final Workspace workspace) {
        workspace.setUrl(workspaceDTO.getUrl());
        final Schedule schedule = workspaceDTO.getSchedule() == null ? null : scheduleRepository.findById(workspaceDTO.getSchedule())
                .orElseThrow(() -> new NotFoundException("schedule not found"));
        workspace.setSchedule(schedule);
        return workspace;
    }

}
