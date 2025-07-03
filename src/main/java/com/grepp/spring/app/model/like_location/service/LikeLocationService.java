package com.grepp.spring.app.model.like_location.service;

import com.grepp.spring.app.model.like_location.domain.LikeLocation;
import com.grepp.spring.app.model.like_location.model.LikeLocationDTO;
import com.grepp.spring.app.model.like_location.repos.LikeLocationRepository;
import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.member.repos.MemberRepository;
import com.grepp.spring.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LikeLocationService {

    private final LikeLocationRepository likeLocationRepository;
    private final MemberRepository memberRepository;

    public LikeLocationService(final LikeLocationRepository likeLocationRepository,
            final MemberRepository memberRepository) {
        this.likeLocationRepository = likeLocationRepository;
        this.memberRepository = memberRepository;
    }

    public List<LikeLocationDTO> findAll() {
        final List<LikeLocation> likeLocations = likeLocationRepository.findAll(Sort.by("likeLocationId"));
        return likeLocations.stream()
                .map(likeLocation -> mapToDTO(likeLocation, new LikeLocationDTO()))
                .toList();
    }

    public LikeLocationDTO get(final Long likeLocationId) {
        return likeLocationRepository.findById(likeLocationId)
                .map(likeLocation -> mapToDTO(likeLocation, new LikeLocationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LikeLocationDTO likeLocationDTO) {
        final LikeLocation likeLocation = new LikeLocation();
        mapToEntity(likeLocationDTO, likeLocation);
        return likeLocationRepository.save(likeLocation).getLikeLocationId();
    }

    public void update(final Long likeLocationId, final LikeLocationDTO likeLocationDTO) {
        final LikeLocation likeLocation = likeLocationRepository.findById(likeLocationId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(likeLocationDTO, likeLocation);
        likeLocationRepository.save(likeLocation);
    }

    public void delete(final Long likeLocationId) {
        likeLocationRepository.deleteById(likeLocationId);
    }

    private LikeLocationDTO mapToDTO(final LikeLocation likeLocation,
            final LikeLocationDTO likeLocationDTO) {
        likeLocationDTO.setLikeLocationId(likeLocation.getLikeLocationId());
        likeLocationDTO.setLongitude(likeLocation.getLongitude());
        likeLocationDTO.setLatitude(likeLocation.getLatitude());
        likeLocationDTO.setLocationName(likeLocation.getLocationName());
        likeLocationDTO.setUser(likeLocation.getUser() == null ? null : likeLocation.getUser().getUserId());
        return likeLocationDTO;
    }

    private LikeLocation mapToEntity(final LikeLocationDTO likeLocationDTO,
            final LikeLocation likeLocation) {
        likeLocation.setLongitude(likeLocationDTO.getLongitude());
        likeLocation.setLatitude(likeLocationDTO.getLatitude());
        likeLocation.setLocationName(likeLocationDTO.getLocationName());
        final Member user = likeLocationDTO.getUser() == null ? null : memberRepository.findById(likeLocationDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        likeLocation.setUser(user);
        return likeLocation;
    }

    public boolean userExists(final String userId) {
        return likeLocationRepository.existsByUserUserIdIgnoreCase(userId);
    }

}
