package com.grepp.spring.detail.service;

import com.grepp.spring.detail.domain.Detail;
import com.grepp.spring.detail.model.DetailDTO;
import com.grepp.spring.detail.repos.DetailRepository;
import com.grepp.spring.location_candidate.domain.LocationCandidate;
import com.grepp.spring.location_candidate.repos.LocationCandidateRepository;
import com.grepp.spring.schedule.domain.Schedule;
import com.grepp.spring.schedule.repos.ScheduleRepository;
import com.grepp.spring.util.NotFoundException;
import com.grepp.spring.util.ReferencedWarning;
import com.grepp.spring.workspace.domain.Workspace;
import com.grepp.spring.workspace.repos.WorkspaceRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DetailService {

    private final DetailRepository detailRepository;
    private final ScheduleRepository scheduleRepository;
    private final WorkspaceRepository workspaceRepository;
    private final LocationCandidateRepository locationCandidateRepository;

    public DetailService(final DetailRepository detailRepository,
            final ScheduleRepository scheduleRepository,
            final WorkspaceRepository workspaceRepository,
            final LocationCandidateRepository locationCandidateRepository) {
        this.detailRepository = detailRepository;
        this.scheduleRepository = scheduleRepository;
        this.workspaceRepository = workspaceRepository;
        this.locationCandidateRepository = locationCandidateRepository;
    }

    public List<DetailDTO> findAll() {
        final List<Detail> details = detailRepository.findAll(Sort.by("detailId"));
        return details.stream()
                .map(detail -> mapToDTO(detail, new DetailDTO()))
                .toList();
    }

    public DetailDTO get(final Long detailId) {
        return detailRepository.findById(detailId)
                .map(detail -> mapToDTO(detail, new DetailDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DetailDTO detailDTO) {
        final Detail detail = new Detail();
        mapToEntity(detailDTO, detail);
        return detailRepository.save(detail).getDetailId();
    }

    public void update(final Long detailId, final DetailDTO detailDTO) {
        final Detail detail = detailRepository.findById(detailId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(detailDTO, detail);
        detailRepository.save(detail);
    }

    public void delete(final Long detailId) {
        detailRepository.deleteById(detailId);
    }

    private DetailDTO mapToDTO(final Detail detail, final DetailDTO detailDTO) {
        detailDTO.setDetailId(detail.getDetailId());
        detailDTO.setLocation(detail.getLocation());
        detailDTO.setSchedule(detail.getSchedule() == null ? null : detail.getSchedule().getScheduleId());
        return detailDTO;
    }

    private Detail mapToEntity(final DetailDTO detailDTO, final Detail detail) {
        detail.setLocation(detailDTO.getLocation());
        final Schedule schedule = detailDTO.getSchedule() == null ? null : scheduleRepository.findById(detailDTO.getSchedule())
                .orElseThrow(() -> new NotFoundException("schedule not found"));
        detail.setSchedule(schedule);
        return detail;
    }

    public boolean scheduleExists(final Long scheduleId) {
        return detailRepository.existsByScheduleScheduleId(scheduleId);
    }

    public ReferencedWarning getReferencedWarning(final Long detailId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Detail detail = detailRepository.findById(detailId)
                .orElseThrow(NotFoundException::new);
        final Workspace detailWorkspace = workspaceRepository.findFirstByDetail(detail);
        if (detailWorkspace != null) {
            referencedWarning.setKey("detail.workspace.detail.referenced");
            referencedWarning.addParam(detailWorkspace.getWorkspaceId());
            return referencedWarning;
        }
        final LocationCandidate detailLocationCandidate = locationCandidateRepository.findFirstByDetail(detail);
        if (detailLocationCandidate != null) {
            referencedWarning.setKey("detail.locationCandidate.detail.referenced");
            referencedWarning.addParam(detailLocationCandidate.getLocationCandidateId());
            return referencedWarning;
        }
        return null;
    }

}
