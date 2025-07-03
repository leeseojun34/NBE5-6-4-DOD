package com.grepp.spring.app.model.group.service;

import com.grepp.spring.app.model.event.domain.Event;
import com.grepp.spring.app.model.event.repos.EventRepository;
import com.grepp.spring.app.model.group.domain.Group;
import com.grepp.spring.app.model.group.model.GroupDTO;
import com.grepp.spring.app.model.group.repos.GroupRepository;
import com.grepp.spring.app.model.group_user.domain.GroupUser;
import com.grepp.spring.app.model.group_user.repos.GroupUserRepository;
import com.grepp.spring.util.NotFoundException;
import com.grepp.spring.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupUserRepository groupUserRepository;
    private final EventRepository eventRepository;

    public GroupService(final GroupRepository groupRepository,
            final GroupUserRepository groupUserRepository, final EventRepository eventRepository) {
        this.groupRepository = groupRepository;
        this.groupUserRepository = groupUserRepository;
        this.eventRepository = eventRepository;
    }

    public List<GroupDTO> findAll() {
        final List<Group> groups = groupRepository.findAll(Sort.by("groupId"));
        return groups.stream()
                .map(group -> mapToDTO(group, new GroupDTO()))
                .toList();
    }

    public GroupDTO get(final Long groupId) {
        return groupRepository.findById(groupId)
                .map(group -> mapToDTO(group, new GroupDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final GroupDTO groupDTO) {
        final Group group = new Group();
        mapToEntity(groupDTO, group);
        return groupRepository.save(group).getGroupId();
    }

    public void update(final Long groupId, final GroupDTO groupDTO) {
        final Group group = groupRepository.findById(groupId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(groupDTO, group);
        groupRepository.save(group);
    }

    public void delete(final Long groupId) {
        groupRepository.deleteById(groupId);
    }

    private GroupDTO mapToDTO(final Group group, final GroupDTO groupDTO) {
        groupDTO.setGroupId(group.getGroupId());
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

    public ReferencedWarning getReferencedWarning(final Long groupId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Group group = groupRepository.findById(groupId)
                .orElseThrow(NotFoundException::new);
        final GroupUser groupGroupUser = groupUserRepository.findFirstByGroup(group);
        if (groupGroupUser != null) {
            referencedWarning.setKey("group.groupUser.group.referenced");
            referencedWarning.addParam(groupGroupUser.getGroupUserId());
            return referencedWarning;
        }
        final Event groupEvent = eventRepository.findFirstByGroup(group);
        if (groupEvent != null) {
            referencedWarning.setKey("group.event.group.referenced");
            referencedWarning.addParam(groupEvent.getEventId());
            return referencedWarning;
        }
        return null;
    }

}
