package com.grepp.spring.app.model.like_location.repos;

import com.grepp.spring.app.model.like_location.domain.LikeLocation;
import com.grepp.spring.app.model.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LikeLocationRepository extends JpaRepository<LikeLocation, Long> {

    LikeLocation findFirstByUser(Member member);

    boolean existsByUserUserIdIgnoreCase(String userId);

}
