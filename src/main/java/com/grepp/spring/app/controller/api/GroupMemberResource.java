package com.grepp.spring.app.controller.api;

import com.grepp.spring.app.model.group.domain.Group;
import com.grepp.spring.app.model.group.repos.GroupRepository;
import com.grepp.spring.app.model.group_member.model.GroupMemberDTO;
import com.grepp.spring.app.model.group_member.service.GroupMemberService;
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
@RequestMapping(value = "/api/groupMembers", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupMemberResource {

    private final GroupMemberService groupMemberService;
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;

    public GroupMemberResource(final GroupMemberService groupMemberService,
            final MemberRepository memberRepository, final GroupRepository groupRepository) {
        this.groupMemberService = groupMemberService;
        this.memberRepository = memberRepository;
        this.groupRepository = groupRepository;
    }

    @GetMapping
    public ResponseEntity<List<GroupMemberDTO>> getAllGroupMembers() {
        return ResponseEntity.ok(groupMemberService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupMemberDTO> getGroupMember(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(groupMemberService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createGroupMember(
            @RequestBody @Valid final GroupMemberDTO groupMemberDTO) {
        final Long createdId = groupMemberService.create(groupMemberDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateGroupMember(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final GroupMemberDTO groupMemberDTO) {
        groupMemberService.update(id, groupMemberDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteGroupMember(@PathVariable(name = "id") final Long id) {
        groupMemberService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/memberValues")
    public ResponseEntity<Map<String, String>> getMemberValues() {
        return ResponseEntity.ok(memberRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Member::getId, Member::getPassword)));
    }

    @GetMapping("/groupValues")
    public ResponseEntity<Map<Long, String>> getGroupValues() {
        return ResponseEntity.ok(groupRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Group::getId, Group::getName)));
    }

}
