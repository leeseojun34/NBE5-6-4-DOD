package com.grepp.spring.user_vote.service;

import com.grepp.spring.location_candidate.domain.LocationCandidate;
import com.grepp.spring.location_candidate.repos.LocationCandidateRepository;
import com.grepp.spring.user_vote.domain.UserVote;
import com.grepp.spring.user_vote.model.UserVoteDTO;
import com.grepp.spring.user_vote.repos.UserVoteRepository;
import com.grepp.spring.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserVoteService {

    private final UserVoteRepository userVoteRepository;
    private final LocationCandidateRepository locationCandidateRepository;

    public UserVoteService(final UserVoteRepository userVoteRepository,
            final LocationCandidateRepository locationCandidateRepository) {
        this.userVoteRepository = userVoteRepository;
        this.locationCandidateRepository = locationCandidateRepository;
    }

    public List<UserVoteDTO> findAll() {
        final List<UserVote> userVotes = userVoteRepository.findAll(Sort.by("userVoteId"));
        return userVotes.stream()
                .map(userVote -> mapToDTO(userVote, new UserVoteDTO()))
                .toList();
    }

    public UserVoteDTO get(final Long userVoteId) {
        return userVoteRepository.findById(userVoteId)
                .map(userVote -> mapToDTO(userVote, new UserVoteDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserVoteDTO userVoteDTO) {
        final UserVote userVote = new UserVote();
        mapToEntity(userVoteDTO, userVote);
        return userVoteRepository.save(userVote).getUserVoteId();
    }

    public void update(final Long userVoteId, final UserVoteDTO userVoteDTO) {
        final UserVote userVote = userVoteRepository.findById(userVoteId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userVoteDTO, userVote);
        userVoteRepository.save(userVote);
    }

    public void delete(final Long userVoteId) {
        userVoteRepository.deleteById(userVoteId);
    }

    private UserVoteDTO mapToDTO(final UserVote userVote, final UserVoteDTO userVoteDTO) {
        userVoteDTO.setUserVoteId(userVote.getUserVoteId());
        userVoteDTO.setUserId(userVote.getUserId());
        userVoteDTO.setLocationCandidate(userVote.getLocationCandidate() == null ? null : userVote.getLocationCandidate().getLocationCandidateId());
        return userVoteDTO;
    }

    private UserVote mapToEntity(final UserVoteDTO userVoteDTO, final UserVote userVote) {
        userVote.setUserId(userVoteDTO.getUserId());
        final LocationCandidate locationCandidate = userVoteDTO.getLocationCandidate() == null ? null : locationCandidateRepository.findById(userVoteDTO.getLocationCandidate())
                .orElseThrow(() -> new NotFoundException("locationCandidate not found"));
        userVote.setLocationCandidate(locationCandidate);
        return userVote;
    }

    public boolean locationCandidateExists(final Long locationCandidateId) {
        return userVoteRepository.existsByLocationCandidateLocationCandidateId(locationCandidateId);
    }

}
