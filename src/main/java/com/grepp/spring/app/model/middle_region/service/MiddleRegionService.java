package com.grepp.spring.app.model.middle_region.service;

import com.grepp.spring.app.model.location.domain.Location;
import com.grepp.spring.app.model.location.repos.LocationRepository;
import com.grepp.spring.app.model.middle_region.domain.MiddleRegion;
import com.grepp.spring.app.model.middle_region.model.MiddleRegionDTO;
import com.grepp.spring.app.model.middle_region.repos.MiddleRegionRepository;
import com.grepp.spring.app.model.schedule_member.domain.ScheduleMember;
import com.grepp.spring.app.model.schedule_member.repos.ScheduleMemberRepository;
import com.grepp.spring.util.NotFoundException;
import com.grepp.spring.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MiddleRegionService {

    private final MiddleRegionRepository middleRegionRepository;
    private final LocationRepository locationRepository;
    private final ScheduleMemberRepository scheduleMemberRepository;

    public MiddleRegionService(final MiddleRegionRepository middleRegionRepository,
            final LocationRepository locationRepository,
            final ScheduleMemberRepository scheduleMemberRepository) {
        this.middleRegionRepository = middleRegionRepository;
        this.locationRepository = locationRepository;
        this.scheduleMemberRepository = scheduleMemberRepository;
    }

    public List<MiddleRegionDTO> findAll() {
        final List<MiddleRegion> middleRegions = middleRegionRepository.findAll(Sort.by("id"));
        return middleRegions.stream()
                .map(middleRegion -> mapToDTO(middleRegion, new MiddleRegionDTO()))
                .toList();
    }

    public MiddleRegionDTO get(final Long id) {
        return middleRegionRepository.findById(id)
                .map(middleRegion -> mapToDTO(middleRegion, new MiddleRegionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final MiddleRegionDTO middleRegionDTO) {
        final MiddleRegion middleRegion = new MiddleRegion();
        mapToEntity(middleRegionDTO, middleRegion);
        return middleRegionRepository.save(middleRegion).getId();
    }

    public void update(final Long id, final MiddleRegionDTO middleRegionDTO) {
        final MiddleRegion middleRegion = middleRegionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(middleRegionDTO, middleRegion);
        middleRegionRepository.save(middleRegion);
    }

    public void delete(final Long id) {
        middleRegionRepository.deleteById(id);
    }

    private MiddleRegionDTO mapToDTO(final MiddleRegion middleRegion,
            final MiddleRegionDTO middleRegionDTO) {
        middleRegionDTO.setId(middleRegion.getId());
        middleRegionDTO.setLatitude(middleRegion.getLatitude());
        middleRegionDTO.setLongitude(middleRegion.getLongitude());
        return middleRegionDTO;
    }

    private MiddleRegion mapToEntity(final MiddleRegionDTO middleRegionDTO,
            final MiddleRegion middleRegion) {
        middleRegion.setLatitude(middleRegionDTO.getLatitude());
        middleRegion.setLongitude(middleRegionDTO.getLongitude());
        return middleRegion;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final MiddleRegion middleRegion = middleRegionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Location middleRegionLocation = locationRepository.findFirstByMiddleRegion(middleRegion);
        if (middleRegionLocation != null) {
            referencedWarning.setKey("middleRegion.location.middleRegion.referenced");
            referencedWarning.addParam(middleRegionLocation.getId());
            return referencedWarning;
        }
        final ScheduleMember middleRegionScheduleMember = scheduleMemberRepository.findFirstByMiddleRegion(middleRegion);
        if (middleRegionScheduleMember != null) {
            referencedWarning.setKey("middleRegion.scheduleMember.middleRegion.referenced");
            referencedWarning.addParam(middleRegionScheduleMember.getId());
            return referencedWarning;
        }
        return null;
    }

}
