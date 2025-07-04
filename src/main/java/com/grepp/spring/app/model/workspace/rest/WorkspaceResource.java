package com.grepp.spring.app.model.workspace.rest;

import com.grepp.spring.app.model.schedule.domain.Schedule;
import com.grepp.spring.app.model.schedule.repos.ScheduleRepository;
import com.grepp.spring.util.CustomCollectors;
import com.grepp.spring.app.model.workspace.model.WorkspaceDTO;
import com.grepp.spring.app.model.workspace.service.WorkspaceService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/workspaces", produces = MediaType.APPLICATION_JSON_VALUE)
public class WorkspaceResource {

    private final WorkspaceService workspaceService;
    private final ScheduleRepository scheduleRepository;

    public WorkspaceResource(final WorkspaceService workspaceService,
            final ScheduleRepository scheduleRepository) {
        this.workspaceService = workspaceService;
        this.scheduleRepository = scheduleRepository;
    }

    @GetMapping
    public ResponseEntity<List<WorkspaceDTO>> getAllWorkspaces() {
        return ResponseEntity.ok(workspaceService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkspaceDTO> getWorkspace(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(workspaceService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createWorkspace(
            @RequestBody @Valid final WorkspaceDTO workspaceDTO) {
        final Long createdId = workspaceService.create(workspaceDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateWorkspace(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final WorkspaceDTO workspaceDTO) {
        workspaceService.update(id, workspaceDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteWorkspace(@PathVariable(name = "id") final Long id) {
        workspaceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/scheduleValues")
    public ResponseEntity<Map<Long, String>> getScheduleValues() {
        return ResponseEntity.ok(scheduleRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Schedule::getId, Schedule::getStatus)));
    }

}
