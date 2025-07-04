package com.grepp.spring.app.model.favorite_location.repos;

import com.grepp.spring.app.model.favorite_location.domain.FavoriteLocation;
import com.grepp.spring.app.model.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FavoriteLocationRepository extends JpaRepository<FavoriteLocation, Long> {

    FavoriteLocation findFirstByMember(Member member);

    boolean existsByMemberIdIgnoreCase(String id);

}
