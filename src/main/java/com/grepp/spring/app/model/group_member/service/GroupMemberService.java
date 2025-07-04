package com.grepp.spring.app.model.group_member.service;

import com.grepp.spring.app.model.group.domain.Group;
import com.grepp.spring.app.model.group.repos.GroupRepository;
import com.grepp.spring.app.model.group_member.domain.GroupMember;
import com.grepp.spring.app.model.group_member.model.GroupMemberDTO;
import com.grepp.spring.app.model.group_member.repos.GroupMemberRepository;
import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.member.repos.MemberRepository;
import com.grepp.spring.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class GroupMemberService {

    private final GroupMemberRepository groupMemberRepository;
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;

    public GroupMemberService(final GroupMemberRepository groupMemberRepository,
            final MemberRepository memberRepository, final GroupRepository groupRepository) {
        this.groupMemberRepository = groupMemberRepository;
        this.memberRepository = memberRepository;
        this.groupRepository = groupRepository;
    }

    public List<GroupMemberDTO> findAll() {
        final List<GroupMember> groupMembers = groupMemberRepository.findAll(Sort.by("id"));
        return groupMembers.stream()
                .map(groupMember -> mapToDTO(groupMember, new GroupMemberDTO()))
                .toList();
    }

    public GroupMemberDTO get(final Long id) {
        return groupMemberRepository.findById(id)
                .map(groupMember -> mapToDTO(groupMember, new GroupMemberDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final GroupMemberDTO groupMemberDTO) {
        final GroupMember groupMember = new GroupMember();
        mapToEntity(groupMemberDTO, groupMember);
        return groupMemberRepository.save(groupMember).getId();
    }

    public void update(final Long id, final GroupMemberDTO groupMemberDTO) {
        final GroupMember groupMember = groupMemberRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(groupMemberDTO, groupMember);
        groupMemberRepository.save(groupMember);
    }

    public void delete(final Long id) {
        groupMemberRepository.deleteById(id);
    }

    private GroupMemberDTO mapToDTO(final GroupMember groupMember,
            final GroupMemberDTO groupMemberDTO) {
        groupMemberDTO.setId(groupMember.getId());
        groupMemberDTO.setRole(groupMember.getRole());
        groupMemberDTO.setMember(groupMember.getMember() == null ? null : groupMember.getMember().getId());
        groupMemberDTO.setGroup(groupMember.getGroup() == null ? null : groupMember.getGroup().getId());
        return groupMemberDTO;
    }

    private GroupMember mapToEntity(final GroupMemberDTO groupMemberDTO,
            final GroupMember groupMember) {
        groupMember.setRole(groupMemberDTO.getRole());
        final Member member = groupMemberDTO.getMember() == null ? null : memberRepository.findById(groupMemberDTO.getMember())
                .orElseThrow(() -> new NotFoundException("member not found"));
        groupMember.setMember(member);
        final Group group = groupMemberDTO.getGroup() == null ? null : groupRepository.findById(groupMemberDTO.getGroup())
                .orElseThrow(() -> new NotFoundException("group not found"));
        groupMember.setGroup(group);
        return groupMember;
    }

}
