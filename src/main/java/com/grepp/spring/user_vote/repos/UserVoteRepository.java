package com.grepp.spring.user_vote.repos;

import com.grepp.spring.location_candidate.domain.LocationCandidate;
import com.grepp.spring.user_vote.domain.UserVote;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserVoteRepository extends JpaRepository<UserVote, Long> {

    UserVote findFirstByLocationCandidate(LocationCandidate locationCandidate);

    boolean existsByLocationCandidateLocationCandidateId(Long locationCandidateId);

}
