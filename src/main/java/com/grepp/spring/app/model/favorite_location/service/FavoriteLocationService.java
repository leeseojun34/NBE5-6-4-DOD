package com.grepp.spring.app.model.favorite_location.service;

import com.grepp.spring.app.model.favorite_location.domain.FavoriteLocation;
import com.grepp.spring.app.model.favorite_location.model.FavoriteLocationDTO;
import com.grepp.spring.app.model.favorite_location.repos.FavoriteLocationRepository;
import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.member.repos.MemberRepository;
import com.grepp.spring.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class FavoriteLocationService {

    private final FavoriteLocationRepository favoriteLocationRepository;
    private final MemberRepository memberRepository;

    public FavoriteLocationService(final FavoriteLocationRepository favoriteLocationRepository,
            final MemberRepository memberRepository) {
        this.favoriteLocationRepository = favoriteLocationRepository;
        this.memberRepository = memberRepository;
    }

    public List<FavoriteLocationDTO> findAll() {
        final List<FavoriteLocation> favoriteLocations = favoriteLocationRepository.findAll(Sort.by("id"));
        return favoriteLocations.stream()
                .map(favoriteLocation -> mapToDTO(favoriteLocation, new FavoriteLocationDTO()))
                .toList();
    }

    public FavoriteLocationDTO get(final Long id) {
        return favoriteLocationRepository.findById(id)
                .map(favoriteLocation -> mapToDTO(favoriteLocation, new FavoriteLocationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final FavoriteLocationDTO favoriteLocationDTO) {
        final FavoriteLocation favoriteLocation = new FavoriteLocation();
        mapToEntity(favoriteLocationDTO, favoriteLocation);
        return favoriteLocationRepository.save(favoriteLocation).getId();
    }

    public void update(final Long id, final FavoriteLocationDTO favoriteLocationDTO) {
        final FavoriteLocation favoriteLocation = favoriteLocationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(favoriteLocationDTO, favoriteLocation);
        favoriteLocationRepository.save(favoriteLocation);
    }

    public void delete(final Long id) {
        favoriteLocationRepository.deleteById(id);
    }

    private FavoriteLocationDTO mapToDTO(final FavoriteLocation favoriteLocation,
            final FavoriteLocationDTO favoriteLocationDTO) {
        favoriteLocationDTO.setId(favoriteLocation.getId());
        favoriteLocationDTO.setLongitude(favoriteLocation.getLongitude());
        favoriteLocationDTO.setLatitude(favoriteLocation.getLatitude());
        favoriteLocationDTO.setName(favoriteLocation.getName());
        favoriteLocationDTO.setMember(favoriteLocation.getMember() == null ? null : favoriteLocation.getMember().getId());
        return favoriteLocationDTO;
    }

    private FavoriteLocation mapToEntity(final FavoriteLocationDTO favoriteLocationDTO,
            final FavoriteLocation favoriteLocation) {
        favoriteLocation.setLongitude(favoriteLocationDTO.getLongitude());
        favoriteLocation.setLatitude(favoriteLocationDTO.getLatitude());
        favoriteLocation.setName(favoriteLocationDTO.getName());
        final Member member = favoriteLocationDTO.getMember() == null ? null : memberRepository.findById(favoriteLocationDTO.getMember())
                .orElseThrow(() -> new NotFoundException("member not found"));
        favoriteLocation.setMember(member);
        return favoriteLocation;
    }

    public boolean memberExists(final String id) {
        return favoriteLocationRepository.existsByMemberIdIgnoreCase(id);
    }

}
