package com.grepp.spring.group.rest;

import com.grepp.spring.group.model.GroupDTO;
import com.grepp.spring.group.service.GroupService;
import com.grepp.spring.util.ReferencedException;
import com.grepp.spring.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping(value = "/api/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupResource {

    private final GroupService groupService;

    public GroupResource(final GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        return ResponseEntity.ok(groupService.findAll());
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDTO> getGroup(@PathVariable(name = "groupId") final Long groupId) {
        return ResponseEntity.ok(groupService.get(groupId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createGroup(@RequestBody @Valid final GroupDTO groupDTO) {
        final Long createdGroupId = groupService.create(groupDTO);
        return new ResponseEntity<>(createdGroupId, HttpStatus.CREATED);
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<Long> updateGroup(@PathVariable(name = "groupId") final Long groupId,
            @RequestBody @Valid final GroupDTO groupDTO) {
        groupService.update(groupId, groupDTO);
        return ResponseEntity.ok(groupId);
    }

    @DeleteMapping("/{groupId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteGroup(@PathVariable(name = "groupId") final Long groupId) {
        final ReferencedWarning referencedWarning = groupService.getReferencedWarning(groupId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        groupService.delete(groupId);
        return ResponseEntity.noContent().build();
    }

}
