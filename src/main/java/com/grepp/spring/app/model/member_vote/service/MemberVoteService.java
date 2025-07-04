package com.grepp.spring.app.model.member_vote.service;

import com.grepp.spring.app.model.location.domain.Location;
import com.grepp.spring.app.model.location.repos.LocationRepository;
import com.grepp.spring.app.model.member_vote.domain.MemberVote;
import com.grepp.spring.app.model.member_vote.model.MemberVoteDTO;
import com.grepp.spring.app.model.member_vote.repos.MemberVoteRepository;
import com.grepp.spring.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MemberVoteService {

    private final MemberVoteRepository memberVoteRepository;
    private final LocationRepository locationRepository;

    public MemberVoteService(final MemberVoteRepository memberVoteRepository,
            final LocationRepository locationRepository) {
        this.memberVoteRepository = memberVoteRepository;
        this.locationRepository = locationRepository;
    }

    public List<MemberVoteDTO> findAll() {
        final List<MemberVote> memberVotes = memberVoteRepository.findAll(Sort.by("id"));
        return memberVotes.stream()
                .map(memberVote -> mapToDTO(memberVote, new MemberVoteDTO()))
                .toList();
    }

    public MemberVoteDTO get(final Long id) {
        return memberVoteRepository.findById(id)
                .map(memberVote -> mapToDTO(memberVote, new MemberVoteDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final MemberVoteDTO memberVoteDTO) {
        final MemberVote memberVote = new MemberVote();
        mapToEntity(memberVoteDTO, memberVote);
        return memberVoteRepository.save(memberVote).getId();
    }

    public void update(final Long id, final MemberVoteDTO memberVoteDTO) {
        final MemberVote memberVote = memberVoteRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(memberVoteDTO, memberVote);
        memberVoteRepository.save(memberVote);
    }

    public void delete(final Long id) {
        memberVoteRepository.deleteById(id);
    }

    private MemberVoteDTO mapToDTO(final MemberVote memberVote, final MemberVoteDTO memberVoteDTO) {
        memberVoteDTO.setId(memberVote.getId());
        memberVoteDTO.setVoter(memberVote.getVoter());
        memberVoteDTO.setLocation(memberVote.getLocation() == null ? null : memberVote.getLocation().getId());
        return memberVoteDTO;
    }

    private MemberVote mapToEntity(final MemberVoteDTO memberVoteDTO, final MemberVote memberVote) {
        memberVote.setVoter(memberVoteDTO.getVoter());
        final Location location = memberVoteDTO.getLocation() == null ? null : locationRepository.findById(memberVoteDTO.getLocation())
                .orElseThrow(() -> new NotFoundException("location not found"));
        memberVote.setLocation(location);
        return memberVote;
    }

}
