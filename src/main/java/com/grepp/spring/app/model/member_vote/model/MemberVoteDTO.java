package com.grepp.spring.app.model.member_vote.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MemberVoteDTO {

    private Long id;

    @NotNull
    private Long voter;

    private Long location;

}
