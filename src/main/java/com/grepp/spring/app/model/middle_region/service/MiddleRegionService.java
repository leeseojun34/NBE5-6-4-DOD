package com.grepp.spring.app.model.middle_region.service;

import com.grepp.spring.app.model.depart_region.domain.DepartRegion;
import com.grepp.spring.app.model.depart_region.repos.DepartRegionRepository;
import com.grepp.spring.app.model.location.domain.Location;
import com.grepp.spring.app.model.location.repos.LocationRepository;
import com.grepp.spring.app.model.middle_region.domain.MiddleRegion;
import com.grepp.spring.app.model.middle_region.model.MiddleRegionDTO;
import com.grepp.spring.app.model.middle_region.repos.MiddleRegionRepository;
import com.grepp.spring.util.NotFoundException;
import com.grepp.spring.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MiddleRegionService {

    private final MiddleRegionRepository middleRegionRepository;
    private final DepartRegionRepository departRegionRepository;
    private final LocationRepository locationRepository;

    public MiddleRegionService(final MiddleRegionRepository middleRegionRepository,
            final DepartRegionRepository departRegionRepository,
            final LocationRepository locationRepository) {
        this.middleRegionRepository = middleRegionRepository;
        this.departRegionRepository = departRegionRepository;
        this.locationRepository = locationRepository;
    }

    public List<MiddleRegionDTO> findAll() {
        final List<MiddleRegion> middleRegions = middleRegionRepository.findAll(Sort.by("middleRegionId"));
        return middleRegions.stream()
                .map(middleRegion -> mapToDTO(middleRegion, new MiddleRegionDTO()))
                .toList();
    }

    public MiddleRegionDTO get(final Long middleRegionId) {
        return middleRegionRepository.findById(middleRegionId)
                .map(middleRegion -> mapToDTO(middleRegion, new MiddleRegionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final MiddleRegionDTO middleRegionDTO) {
        final MiddleRegion middleRegion = new MiddleRegion();
        mapToEntity(middleRegionDTO, middleRegion);
        return middleRegionRepository.save(middleRegion).getMiddleRegionId();
    }

    public void update(final Long middleRegionId, final MiddleRegionDTO middleRegionDTO) {
        final MiddleRegion middleRegion = middleRegionRepository.findById(middleRegionId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(middleRegionDTO, middleRegion);
        middleRegionRepository.save(middleRegion);
    }

    public void delete(final Long middleRegionId) {
        middleRegionRepository.deleteById(middleRegionId);
    }

    private MiddleRegionDTO mapToDTO(final MiddleRegion middleRegion,
            final MiddleRegionDTO middleRegionDTO) {
        middleRegionDTO.setMiddleRegionId(middleRegion.getMiddleRegionId());
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

    public ReferencedWarning getReferencedWarning(final Long middleRegionId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final MiddleRegion middleRegion = middleRegionRepository.findById(middleRegionId)
                .orElseThrow(NotFoundException::new);
        final DepartRegion middleRegionDepartRegion = departRegionRepository.findFirstByMiddleRegion(middleRegion);
        if (middleRegionDepartRegion != null) {
            referencedWarning.setKey("middleRegion.departRegion.middleRegion.referenced");
            referencedWarning.addParam(middleRegionDepartRegion.getDepartRegionId());
            return referencedWarning;
        }
        final Location middleRegionLocation = locationRepository.findFirstByMiddleRegion(middleRegion);
        if (middleRegionLocation != null) {
            referencedWarning.setKey("middleRegion.location.middleRegion.referenced");
            referencedWarning.addParam(middleRegionLocation.getLocationId());
            return referencedWarning;
        }
        return null;
    }

}
