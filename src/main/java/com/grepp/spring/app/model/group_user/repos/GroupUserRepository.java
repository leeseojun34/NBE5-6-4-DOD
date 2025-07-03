package com.grepp.spring.app.model.group_user.repos;

import com.grepp.spring.app.model.group.domain.Group;
import com.grepp.spring.app.model.group_user.domain.GroupUser;
import com.grepp.spring.app.model.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {

    GroupUser findFirstByUser(Member member);

    GroupUser findFirstByGroup(Group group);

}
