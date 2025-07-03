package com.grepp.spring.candidate_date.service;

import com.grepp.spring.candidate_date.domain.CandidateDate;
import com.grepp.spring.candidate_date.model.CandidateDateDTO;
import com.grepp.spring.candidate_date.repos.CandidateDateRepository;
import com.grepp.spring.event.domain.Event;
import com.grepp.spring.event.repos.EventRepository;
import com.grepp.spring.util.NotFoundException;
import java.time.LocalDateTime;
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
        final List<CandidateDate> candidateDates = candidateDateRepository.findAll(Sort.by("candidateDateId"));
        return candidateDates.stream()
                .map(candidateDate -> mapToDTO(candidateDate, new CandidateDateDTO()))
                .toList();
    }

    public CandidateDateDTO get(final Long candidateDateId) {
        return candidateDateRepository.findById(candidateDateId)
                .map(candidateDate -> mapToDTO(candidateDate, new CandidateDateDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CandidateDateDTO candidateDateDTO) {
        final CandidateDate candidateDate = new CandidateDate();
        mapToEntity(candidateDateDTO, candidateDate);
        return candidateDateRepository.save(candidateDate).getCandidateDateId();
    }

    public void update(final Long candidateDateId, final CandidateDateDTO candidateDateDTO) {
        final CandidateDate candidateDate = candidateDateRepository.findById(candidateDateId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(candidateDateDTO, candidateDate);
        candidateDateRepository.save(candidateDate);
    }

    public void delete(final Long candidateDateId) {
        candidateDateRepository.deleteById(candidateDateId);
    }

    private CandidateDateDTO mapToDTO(final CandidateDate candidateDate,
            final CandidateDateDTO candidateDateDTO) {
        candidateDateDTO.setCandidateDateId(candidateDate.getCandidateDateId());
        candidateDateDTO.setDate(candidateDate.getDate());
        candidateDateDTO.setEvent(candidateDate.getEvent() == null ? null : candidateDate.getEvent().getEventId());
        return candidateDateDTO;
    }

    private CandidateDate mapToEntity(final CandidateDateDTO candidateDateDTO,
            final CandidateDate candidateDate) {
        candidateDate.setDate(candidateDateDTO.getDate());
        final Event event = candidateDateDTO.getEvent() == null ? null : eventRepository.findById(candidateDateDTO.getEvent())
                .orElseThrow(() -> new NotFoundException("event not found"));
        candidateDate.setEvent(event);
        return candidateDate;
    }

    public boolean dateExists(final LocalDateTime date) {
        return candidateDateRepository.existsByDate(date);
    }

}
