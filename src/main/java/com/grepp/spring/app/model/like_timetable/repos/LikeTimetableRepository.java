package com.grepp.spring.app.model.like_timetable.repos;

import com.grepp.spring.app.model.like_timetable.domain.LikeTimetable;
import com.grepp.spring.app.model.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LikeTimetableRepository extends JpaRepository<LikeTimetable, Long> {

    LikeTimetable findFirstByUser(Member member);

}
