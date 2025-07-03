package com.grepp.spring.app.controller.api;

import com.grepp.spring.app.model.group.domain.Group;
import com.grepp.spring.app.model.group.repos.GroupRepository;
import com.grepp.spring.app.model.group_user.model.GroupUserDTO;
import com.grepp.spring.app.model.group_user.service.GroupUserService;
import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.member.repos.MemberRepository;
import com.grepp.spring.util.CustomCollectors;
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
@RequestMapping(value = "/api/groupUsers", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupUserResource {

    private final GroupUserService groupUserService;
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;

    public GroupUserResource(final GroupUserService groupUserService,
            final MemberRepository memberRepository, final GroupRepository groupRepository) {
        this.groupUserService = groupUserService;
        this.memberRepository = memberRepository;
        this.groupRepository = groupRepository;
    }

    @GetMapping
    public ResponseEntity<List<GroupUserDTO>> getAllGroupUsers() {
        return ResponseEntity.ok(groupUserService.findAll());
    }

    @GetMapping("/{groupUserId}")
    public ResponseEntity<GroupUserDTO> getGroupUser(
            @PathVariable(name = "groupUserId") final Long groupUserId) {
        return ResponseEntity.ok(groupUserService.get(groupUserId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createGroupUser(
            @RequestBody @Valid final GroupUserDTO groupUserDTO) {
        final Long createdGroupUserId = groupUserService.create(groupUserDTO);
        return new ResponseEntity<>(createdGroupUserId, HttpStatus.CREATED);
    }

    @PutMapping("/{groupUserId}")
    public ResponseEntity<Long> updateGroupUser(
            @PathVariable(name = "groupUserId") final Long groupUserId,
            @RequestBody @Valid final GroupUserDTO groupUserDTO) {
        groupUserService.update(groupUserId, groupUserDTO);
        return ResponseEntity.ok(groupUserId);
    }

    @DeleteMapping("/{groupUserId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteGroupUser(
            @PathVariable(name = "groupUserId") final Long groupUserId) {
        groupUserService.delete(groupUserId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/userValues")
    public ResponseEntity<Map<String, String>> getUserValues() {
        return ResponseEntity.ok(memberRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Member::getUserId, Member::getPassword)));
    }

    @GetMapping("/groupValues")
    public ResponseEntity<Map<Long, String>> getGroupValues() {
        return ResponseEntity.ok(groupRepository.findAll(Sort.by("groupId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Group::getGroupId, Group::getName)));
    }

}
