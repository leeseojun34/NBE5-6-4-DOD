package com.grepp.spring.app.model.location.service;

import com.grepp.spring.app.model.location.domain.Location;
import com.grepp.spring.app.model.location.model.LocationDTO;
import com.grepp.spring.app.model.location.repos.LocationRepository;
import com.grepp.spring.app.model.member_vote.domain.MemberVote;
import com.grepp.spring.app.model.member_vote.repos.MemberVoteRepository;
import com.grepp.spring.app.model.middle_region.domain.MiddleRegion;
import com.grepp.spring.app.model.middle_region.repos.MiddleRegionRepository;
import com.grepp.spring.util.NotFoundException;
import com.grepp.spring.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final MiddleRegionRepository middleRegionRepository;
    private final MemberVoteRepository memberVoteRepository;

    public LocationService(final LocationRepository locationRepository,
            final MiddleRegionRepository middleRegionRepository,
            final MemberVoteRepository memberVoteRepository) {
        this.locationRepository = locationRepository;
        this.middleRegionRepository = middleRegionRepository;
        this.memberVoteRepository = memberVoteRepository;
    }

    public List<LocationDTO> findAll() {
        final List<Location> locations = locationRepository.findAll(Sort.by("id"));
        return locations.stream()
                .map(location -> mapToDTO(location, new LocationDTO()))
                .toList();
    }

    public LocationDTO get(final Long id) {
        return locationRepository.findById(id)
                .map(location -> mapToDTO(location, new LocationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LocationDTO locationDTO) {
        final Location location = new Location();
        mapToEntity(locationDTO, location);
        return locationRepository.save(location).getId();
    }

    public void update(final Long id, final LocationDTO locationDTO) {
        final Location location = locationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(locationDTO, location);
        locationRepository.save(location);
    }

    public void delete(final Long id) {
        locationRepository.deleteById(id);
    }

    private LocationDTO mapToDTO(final Location location, final LocationDTO locationDTO) {
        locationDTO.setId(location.getId());
        locationDTO.setLatitude(location.getLatitude());
        locationDTO.setLongitude(location.getLongitude());
        locationDTO.setName(location.getName());
        locationDTO.setSuggestedMemberId(location.getSuggestedMemberId());
        locationDTO.setVoteCount(location.getVoteCount());
        locationDTO.setStatus(location.getStatus());
        locationDTO.setMiddleRegion(location.getMiddleRegion() == null ? null : location.getMiddleRegion().getId());
        return locationDTO;
    }

    private Location mapToEntity(final LocationDTO locationDTO, final Location location) {
        location.setLatitude(locationDTO.getLatitude());
        location.setLongitude(locationDTO.getLongitude());
        location.setName(locationDTO.getName());
        location.setSuggestedMemberId(locationDTO.getSuggestedMemberId());
        location.setVoteCount(locationDTO.getVoteCount());
        location.setStatus(locationDTO.getStatus());
        final MiddleRegion middleRegion = locationDTO.getMiddleRegion() == null ? null : middleRegionRepository.findById(locationDTO.getMiddleRegion())
                .orElseThrow(() -> new NotFoundException("middleRegion not found"));
        location.setMiddleRegion(middleRegion);
        return location;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Location location = locationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final MemberVote locationMemberVote = memberVoteRepository.findFirstByLocation(location);
        if (locationMemberVote != null) {
            referencedWarning.setKey("location.memberVote.location.referenced");
            referencedWarning.addParam(locationMemberVote.getId());
            return referencedWarning;
        }
        return null;
    }

}
