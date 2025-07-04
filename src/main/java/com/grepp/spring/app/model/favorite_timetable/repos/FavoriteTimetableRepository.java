package com.grepp.spring.app.model.favorite_timetable.repos;

import com.grepp.spring.app.model.favorite_timetable.domain.FavoriteTimetable;
import com.grepp.spring.app.model.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FavoriteTimetableRepository extends JpaRepository<FavoriteTimetable, Long> {

    FavoriteTimetable findFirstByMember(Member member);

}
