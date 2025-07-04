package com.grepp.spring.app.model.member_vote.repos;

import com.grepp.spring.app.model.location.domain.Location;
import com.grepp.spring.app.model.member_vote.domain.MemberVote;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberVoteRepository extends JpaRepository<MemberVote, Long> {

    MemberVote findFirstByLocation(Location location);

}
