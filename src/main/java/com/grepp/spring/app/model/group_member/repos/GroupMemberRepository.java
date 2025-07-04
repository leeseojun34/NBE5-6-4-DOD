package com.grepp.spring.app.model.group_member.repos;

import com.grepp.spring.app.model.group.domain.Group;
import com.grepp.spring.app.model.group_member.domain.GroupMember;
import com.grepp.spring.app.model.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    GroupMember findFirstByMember(Member member);

    GroupMember findFirstByGroup(Group group);

}
