package com.grepp.spring.app.model.group.service;

import com.grepp.spring.app.model.event.domain.Event;
import com.grepp.spring.app.model.event.repos.EventRepository;
import com.grepp.spring.app.model.group.domain.Group;
import com.grepp.spring.app.model.group.model.GroupDTO;
import com.grepp.spring.app.model.group.repos.GroupRepository;
import com.grepp.spring.app.model.group_member.domain.GroupMember;
import com.grepp.spring.app.model.group_member.repos.GroupMemberRepository;
import com.grepp.spring.util.NotFoundException;
import com.grepp.spring.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final EventRepository eventRepository;

    public GroupService(final GroupRepository groupRepository,
            final GroupMemberRepository groupMemberRepository,
            final EventRepository eventRepository) {
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.eventRepository = eventRepository;
    }

    public List<GroupDTO> findAll() {
        final List<Group> groups = groupRepository.findAll(Sort.by("id"));
        return groups.stream()
                .map(group -> mapToDTO(group, new GroupDTO()))
                .toList();
    }

    public GroupDTO get(final Long id) {
        return groupRepository.findById(id)
                .map(group -> mapToDTO(group, new GroupDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final GroupDTO groupDTO) {
        final Group group = new Group();
        mapToEntity(groupDTO, group);
        return groupRepository.save(group).getId();
    }

    public void update(final Long id, final GroupDTO groupDTO) {
        final Group group = groupRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(groupDTO, group);
        groupRepository.save(group);
    }

    public void delete(final Long id) {
        groupRepository.deleteById(id);
    }

    private GroupDTO mapToDTO(final Group group, final GroupDTO groupDTO) {
        groupDTO.setId(group.getId());
        groupDTO.setName(group.getName());
        groupDTO.setDescription(group.getDescription());
        groupDTO.setIsGrouped(group.getIsGrouped());
        return groupDTO;
    }

    private Group mapToEntity(final GroupDTO groupDTO, final Group group) {
        group.setName(groupDTO.getName());
        group.setDescription(groupDTO.getDescription());
        group.setIsGrouped(groupDTO.getIsGrouped());
        return group;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Group group = groupRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final GroupMember groupGroupMember = groupMemberRepository.findFirstByGroup(group);
        if (groupGroupMember != null) {
            referencedWarning.setKey("group.groupMember.group.referenced");
            referencedWarning.addParam(groupGroupMember.getId());
            return referencedWarning;
        }
        final Event groupEvent = eventRepository.findFirstByGroup(group);
        if (groupEvent != null) {
            referencedWarning.setKey("group.event.group.referenced");
            referencedWarning.addParam(groupEvent.getId());
            return referencedWarning;
        }
        return null;
    }

}
