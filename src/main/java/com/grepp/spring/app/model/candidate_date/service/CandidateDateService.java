package com.grepp.spring.app.model.candidate_date.service;

import com.grepp.spring.app.model.candidate_date.domain.CandidateDate;
import com.grepp.spring.app.model.candidate_date.model.CandidateDateDTO;
import com.grepp.spring.app.model.candidate_date.repos.CandidateDateRepository;
import com.grepp.spring.app.model.event.domain.Event;
import com.grepp.spring.app.model.event.repos.EventRepository;
import com.grepp.spring.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CandidateDateService {

    private final CandidateDateRepository candidateDateRepository;
    private final EventRepository eventRepository;

    public CandidateDateService(final CandidateDateRepository candidateDateRepository,
            final EventRepository eventRepository) {
        this.candidateDateRepository = candidateDateRepository;
        this.eventRepository = eventRepository;
    }

    public List<CandidateDateDTO> findAll() {
        final List<CandidateDate> candidateDates = candidateDateRepository.findAll(Sort.by("id"));
        return candidateDates.stream()
                .map(candidateDate -> mapToDTO(candidateDate, new CandidateDateDTO()))
                .toList();
    }

    public CandidateDateDTO get(final Long id) {
        return candidateDateRepository.findById(id)
                .map(candidateDate -> mapToDTO(candidateDate, new CandidateDateDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CandidateDateDTO candidateDateDTO) {
        final CandidateDate candidateDate = new CandidateDate();
        mapToEntity(candidateDateDTO, candidateDate);
        return candidateDateRepository.save(candidateDate).getId();
    }

    public void update(final Long id, final CandidateDateDTO candidateDateDTO) {
        final CandidateDate candidateDate = candidateDateRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(candidateDateDTO, candidateDate);
        candidateDateRepository.save(candidateDate);
    }

    public void delete(final Long id) {
        candidateDateRepository.deleteById(id);
    }

    private CandidateDateDTO mapToDTO(final CandidateDate candidateDate,
            final CandidateDateDTO candidateDateDTO) {
        candidateDateDTO.setId(candidateDate.getId());
        candidateDateDTO.setStartTime(candidateDate.getStartTime());
        candidateDateDTO.setEndTime(candidateDate.getEndTime());
        candidateDateDTO.setEvent(candidateDate.getEvent() == null ? null : candidateDate.getEvent().getId());
        return candidateDateDTO;
    }

    private CandidateDate mapToEntity(final CandidateDateDTO candidateDateDTO,
            final CandidateDate candidateDate) {
        candidateDate.setStartTime(candidateDateDTO.getStartTime());
        candidateDate.setEndTime(candidateDateDTO.getEndTime());
        final Event event = candidateDateDTO.getEvent() == null ? null : eventRepository.findById(candidateDateDTO.getEvent())
                .orElseThrow(() -> new NotFoundException("event not found"));
        candidateDate.setEvent(event);
        return candidateDate;
    }

}
