package com.grepp.spring.app.model.group_user.service;

import com.grepp.spring.app.model.group.domain.Group;
import com.grepp.spring.app.model.group.repos.GroupRepository;
import com.grepp.spring.app.model.group_user.domain.GroupUser;
import com.grepp.spring.app.model.group_user.model.GroupUserDTO;
import com.grepp.spring.app.model.group_user.repos.GroupUserRepository;
import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.member.repos.MemberRepository;
import com.grepp.spring.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class GroupUserService {

    private final GroupUserRepository groupUserRepository;
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;

    public GroupUserService(final GroupUserRepository groupUserRepository,
            final MemberRepository memberRepository, final GroupRepository groupRepository) {
        this.groupUserRepository = groupUserRepository;
        this.memberRepository = memberRepository;
        this.groupRepository = groupRepository;
    }

    public List<GroupUserDTO> findAll() {
        final List<GroupUser> groupUsers = groupUserRepository.findAll(Sort.by("groupUserId"));
        return groupUsers.stream()
                .map(groupUser -> mapToDTO(groupUser, new GroupUserDTO()))
                .toList();
    }

    public GroupUserDTO get(final Long groupUserId) {
        return groupUserRepository.findById(groupUserId)
                .map(groupUser -> mapToDTO(groupUser, new GroupUserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final GroupUserDTO groupUserDTO) {
        final GroupUser groupUser = new GroupUser();
        mapToEntity(groupUserDTO, groupUser);
        return groupUserRepository.save(groupUser).getGroupUserId();
    }

    public void update(final Long groupUserId, final GroupUserDTO groupUserDTO) {
        final GroupUser groupUser = groupUserRepository.findById(groupUserId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(groupUserDTO, groupUser);
        groupUserRepository.save(groupUser);
    }

    public void delete(final Long groupUserId) {
        groupUserRepository.deleteById(groupUserId);
    }

    private GroupUserDTO mapToDTO(final GroupUser groupUser, final GroupUserDTO groupUserDTO) {
        groupUserDTO.setGroupUserId(groupUser.getGroupUserId());
        groupUserDTO.setGroupRole(groupUser.getGroupRole());
        groupUserDTO.setUser(groupUser.getUser() == null ? null : groupUser.getUser().getUserId());
        groupUserDTO.setGroup(groupUser.getGroup() == null ? null : groupUser.getGroup().getGroupId());
        return groupUserDTO;
    }

    private GroupUser mapToEntity(final GroupUserDTO groupUserDTO, final GroupUser groupUser) {
        groupUser.setGroupRole(groupUserDTO.getGroupRole());
        final Member user = groupUserDTO.getUser() == null ? null : memberRepository.findById(groupUserDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        groupUser.setUser(user);
        final Group group = groupUserDTO.getGroup() == null ? null : groupRepository.findById(groupUserDTO.getGroup())
                .orElseThrow(() -> new NotFoundException("group not found"));
        groupUser.setGroup(group);
        return groupUser;
    }

}
