package com.grepp.spring.app.model.user_vote.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserVoteDTO {

    private Long userVoteId;

    @NotNull
    private Long userId;

    @UserVoteLocationCandidateUnique
    private Long locationCandidate;

}
