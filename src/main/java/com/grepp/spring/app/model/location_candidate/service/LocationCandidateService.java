package com.grepp.spring.app.model.location_candidate.service;

import com.grepp.spring.app.model.detail.domain.Detail;
import com.grepp.spring.app.model.detail.repos.DetailRepository;
import com.grepp.spring.app.model.location_candidate.domain.LocationCandidate;
import com.grepp.spring.app.model.location_candidate.model.LocationCandidateDTO;
import com.grepp.spring.app.model.location_candidate.repos.LocationCandidateRepository;
import com.grepp.spring.app.model.user_vote.domain.UserVote;
import com.grepp.spring.app.model.user_vote.repos.UserVoteRepository;
import com.grepp.spring.util.NotFoundException;
import com.grepp.spring.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LocationCandidateService {

    private final LocationCandidateRepository locationCandidateRepository;
    private final DetailRepository detailRepository;
    private final UserVoteRepository userVoteRepository;

    public LocationCandidateService(final LocationCandidateRepository locationCandidateRepository,
            final DetailRepository detailRepository, final UserVoteRepository userVoteRepository) {
        this.locationCandidateRepository = locationCandidateRepository;
        this.detailRepository = detailRepository;
        this.userVoteRepository = userVoteRepository;
    }

    public List<LocationCandidateDTO> findAll() {
        final List<LocationCandidate> locationCandidates = locationCandidateRepository.findAll(Sort.by("locationCandidateId"));
        return locationCandidates.stream()
                .map(locationCandidate -> mapToDTO(locationCandidate, new LocationCandidateDTO()))
                .toList();
    }

    public LocationCandidateDTO get(final Long locationCandidateId) {
        return locationCandidateRepository.findById(locationCandidateId)
                .map(locationCandidate -> mapToDTO(locationCandidate, new LocationCandidateDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LocationCandidateDTO locationCandidateDTO) {
        final LocationCandidate locationCandidate = new LocationCandidate();
        mapToEntity(locationCandidateDTO, locationCandidate);
        return locationCandidateRepository.save(locationCandidate).getLocationCandidateId();
    }

    public void update(final Long locationCandidateId,
            final LocationCandidateDTO locationCandidateDTO) {
        final LocationCandidate locationCandidate = locationCandidateRepository.findById(locationCandidateId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(locationCandidateDTO, locationCandidate);
        locationCandidateRepository.save(locationCandidate);
    }

    public void delete(final Long locationCandidateId) {
        locationCandidateRepository.deleteById(locationCandidateId);
    }

    private LocationCandidateDTO mapToDTO(final LocationCandidate locationCandidate,
            final LocationCandidateDTO locationCandidateDTO) {
        locationCandidateDTO.setLocationCandidateId(locationCandidate.getLocationCandidateId());
        locationCandidateDTO.setSuggestUserId(locationCandidate.getSuggestUserId());
        locationCandidateDTO.setLocationName(locationCandidate.getLocationName());
        locationCandidateDTO.setLatitude(locationCandidate.getLatitude());
        locationCandidateDTO.setLongitude(locationCandidate.getLongitude());
        locationCandidateDTO.setVoteCount(locationCandidate.getVoteCount());
        locationCandidateDTO.setStatus(locationCandidate.getStatus());
        locationCandidateDTO.setDetail(locationCandidate.getDetail() == null ? null : locationCandidate.getDetail().getDetailId());
        return locationCandidateDTO;
    }

    private LocationCandidate mapToEntity(final LocationCandidateDTO locationCandidateDTO,
            final LocationCandidate locationCandidate) {
        locationCandidate.setSuggestUserId(locationCandidateDTO.getSuggestUserId());
        locationCandidate.setLocationName(locationCandidateDTO.getLocationName());
        locationCandidate.setLatitude(locationCandidateDTO.getLatitude());
        locationCandidate.setLongitude(locationCandidateDTO.getLongitude());
        locationCandidate.setVoteCount(locationCandidateDTO.getVoteCount());
        locationCandidate.setStatus(locationCandidateDTO.getStatus());
        final Detail detail = locationCandidateDTO.getDetail() == null ? null : detailRepository.findById(locationCandidateDTO.getDetail())
                .orElseThrow(() -> new NotFoundException("detail not found"));
        locationCandidate.setDetail(detail);
        return locationCandidate;
    }

    public boolean locationNameExists(final String locationName) {
        return locationCandidateRepository.existsByLocationNameIgnoreCase(locationName);
    }

    public ReferencedWarning getReferencedWarning(final Long locationCandidateId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final LocationCandidate locationCandidate = locationCandidateRepository.findById(locationCandidateId)
                .orElseThrow(NotFoundException::new);
        final UserVote locationCandidateUserVote = userVoteRepository.findFirstByLocationCandidate(locationCandidate);
        if (locationCandidateUserVote != null) {
            referencedWarning.setKey("locationCandidate.userVote.locationCandidate.referenced");
            referencedWarning.addParam(locationCandidateUserVote.getUserVoteId());
            return referencedWarning;
        }
        return null;
    }

}
