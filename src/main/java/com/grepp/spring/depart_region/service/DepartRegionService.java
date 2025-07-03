package com.grepp.spring.depart_region.service;

import com.grepp.spring.depart_region.domain.DepartRegion;
import com.grepp.spring.depart_region.model.DepartRegionDTO;
import com.grepp.spring.depart_region.repos.DepartRegionRepository;
import com.grepp.spring.meeting.domain.Meeting;
import com.grepp.spring.meeting.repos.MeetingRepository;
import com.grepp.spring.middle_region.domain.MiddleRegion;
import com.grepp.spring.middle_region.repos.MiddleRegionRepository;
import com.grepp.spring.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DepartRegionService {

    private final DepartRegionRepository departRegionRepository;
    private final MeetingRepository meetingRepository;
    private final MiddleRegionRepository middleRegionRepository;

    public DepartRegionService(final DepartRegionRepository departRegionRepository,
            final MeetingRepository meetingRepository,
            final MiddleRegionRepository middleRegionRepository) {
        this.departRegionRepository = departRegionRepository;
        this.meetingRepository = meetingRepository;
        this.middleRegionRepository = middleRegionRepository;
    }

    public List<DepartRegionDTO> findAll() {
        final List<DepartRegion> departRegions = departRegionRepository.findAll(Sort.by("departRegionId"));
        return departRegions.stream()
                .map(departRegion -> mapToDTO(departRegion, new DepartRegionDTO()))
                .toList();
    }

    public DepartRegionDTO get(final Long departRegionId) {
        return departRegionRepository.findById(departRegionId)
                .map(departRegion -> mapToDTO(departRegion, new DepartRegionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DepartRegionDTO departRegionDTO) {
        final DepartRegion departRegion = new DepartRegion();
        mapToEntity(departRegionDTO, departRegion);
        return departRegionRepository.save(departRegion).getDepartRegionId();
    }

    public void update(final Long departRegionId, final DepartRegionDTO departRegionDTO) {
        final DepartRegion departRegion = departRegionRepository.findById(departRegionId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(departRegionDTO, departRegion);
        departRegionRepository.save(departRegion);
    }

    public void delete(final Long departRegionId) {
        departRegionRepository.deleteById(departRegionId);
    }

    private DepartRegionDTO mapToDTO(final DepartRegion departRegion,
            final DepartRegionDTO departRegionDTO) {
        departRegionDTO.setDepartRegionId(departRegion.getDepartRegionId());
        departRegionDTO.setDapartLocationName(departRegion.getDapartLocationName());
        departRegionDTO.setLatitude(departRegion.getLatitude());
        departRegionDTO.setLongitude(departRegion.getLongitude());
        departRegionDTO.setMeeting(departRegion.getMeeting() == null ? null : departRegion.getMeeting().getMeetingId());
        departRegionDTO.setMiddleRegion(departRegion.getMiddleRegion() == null ? null : departRegion.getMiddleRegion().getMiddleRegionId());
        return departRegionDTO;
    }

    private DepartRegion mapToEntity(final DepartRegionDTO departRegionDTO,
            final DepartRegion departRegion) {
        departRegion.setDapartLocationName(departRegionDTO.getDapartLocationName());
        departRegion.setLatitude(departRegionDTO.getLatitude());
        departRegion.setLongitude(departRegionDTO.getLongitude());
        final Meeting meeting = departRegionDTO.getMeeting() == null ? null : meetingRepository.findById(departRegionDTO.getMeeting())
                .orElseThrow(() -> new NotFoundException("meeting not found"));
        departRegion.setMeeting(meeting);
        final MiddleRegion middleRegion = departRegionDTO.getMiddleRegion() == null ? null : middleRegionRepository.findById(departRegionDTO.getMiddleRegion())
                .orElseThrow(() -> new NotFoundException("middleRegion not found"));
        departRegion.setMiddleRegion(middleRegion);
        return departRegion;
    }

}
