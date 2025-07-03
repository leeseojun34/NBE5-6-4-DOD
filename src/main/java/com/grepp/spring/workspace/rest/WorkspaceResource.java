package com.grepp.spring.workspace.rest;

import com.grepp.spring.detail.domain.Detail;
import com.grepp.spring.detail.repos.DetailRepository;
import com.grepp.spring.util.CustomCollectors;
import com.grepp.spring.workspace.model.WorkspaceDTO;
import com.grepp.spring.workspace.service.WorkspaceService;
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
    private final DetailRepository detailRepository;

    public WorkspaceResource(final WorkspaceService workspaceService,
            final DetailRepository detailRepository) {
        this.workspaceService = workspaceService;
        this.detailRepository = detailRepository;
    }

    @GetMapping
    public ResponseEntity<List<WorkspaceDTO>> getAllWorkspaces() {
        return ResponseEntity.ok(workspaceService.findAll());
    }

    @GetMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceDTO> getWorkspace(
            @PathVariable(name = "workspaceId") final Long workspaceId) {
        return ResponseEntity.ok(workspaceService.get(workspaceId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createWorkspace(
            @RequestBody @Valid final WorkspaceDTO workspaceDTO) {
        final Long createdWorkspaceId = workspaceService.create(workspaceDTO);
        return new ResponseEntity<>(createdWorkspaceId, HttpStatus.CREATED);
    }

    @PutMapping("/{workspaceId}")
    public ResponseEntity<Long> updateWorkspace(
            @PathVariable(name = "workspaceId") final Long workspaceId,
            @RequestBody @Valid final WorkspaceDTO workspaceDTO) {
        workspaceService.update(workspaceId, workspaceDTO);
        return ResponseEntity.ok(workspaceId);
    }

    @DeleteMapping("/{workspaceId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteWorkspace(
            @PathVariable(name = "workspaceId") final Long workspaceId) {
        workspaceService.delete(workspaceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/detailValues")
    public ResponseEntity<Map<Long, String>> getDetailValues() {
        return ResponseEntity.ok(detailRepository.findAll(Sort.by("detailId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Detail::getDetailId, Detail::getLocation)));
    }

}
