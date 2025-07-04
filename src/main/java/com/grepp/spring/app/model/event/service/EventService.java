package com.grepp.spring.app.model.event.service;

import com.grepp.spring.app.model.candidate_date.domain.CandidateDate;
import com.grepp.spring.app.model.candidate_date.repos.CandidateDateRepository;
import com.grepp.spring.app.model.event.domain.Event;
import com.grepp.spring.app.model.event.model.EventDTO;
import com.grepp.spring.app.model.event.repos.EventRepository;
import com.grepp.spring.app.model.event_member.domain.EventMember;
import com.grepp.spring.app.model.event_member.repos.EventMemberRepository;
import com.grepp.spring.app.model.group.domain.Group;
import com.grepp.spring.app.model.group.repos.GroupRepository;
import com.grepp.spring.app.model.schedule.domain.Schedule;
import com.grepp.spring.app.model.schedule.repos.ScheduleRepository;
import com.grepp.spring.util.NotFoundException;
import com.grepp.spring.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EventService {

    private final EventRepository eventRepository;
    private final GroupRepository groupRepository;
    private final EventMemberRepository eventMemberRepository;
    private final ScheduleRepository scheduleRepository;
    private final CandidateDateRepository candidateDateRepository;

    public EventService(final EventRepository eventRepository,
            final GroupRepository groupRepository,
            final EventMemberRepository eventMemberRepository,
            final ScheduleRepository scheduleRepository,
            final CandidateDateRepository candidateDateRepository) {
        this.eventRepository = eventRepository;
        this.groupRepository = groupRepository;
        this.eventMemberRepository = eventMemberRepository;
        this.scheduleRepository = scheduleRepository;
        this.candidateDateRepository = candidateDateRepository;
    }

    public List<EventDTO> findAll() {
        final List<Event> events = eventRepository.findAll(Sort.by("id"));
        return events.stream()
                .map(event -> mapToDTO(event, new EventDTO()))
                .toList();
    }

    public EventDTO get(final Long id) {
        return eventRepository.findById(id)
                .map(event -> mapToDTO(event, new EventDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EventDTO eventDTO) {
        final Event event = new Event();
        mapToEntity(eventDTO, event);
        return eventRepository.save(event).getId();
    }

    public void update(final Long id, final EventDTO eventDTO) {
        final Event event = eventRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(eventDTO, event);
        eventRepository.save(event);
    }

    public void delete(final Long id) {
        eventRepository.deleteById(id);
    }

    private EventDTO mapToDTO(final Event event, final EventDTO eventDTO) {
        eventDTO.setId(event.getId());
        eventDTO.setTitle(event.getTitle());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setMeetingType(event.getMeetingType());
        eventDTO.setMaxMember(event.getMaxMember());
        eventDTO.setGroup(event.getGroup() == null ? null : event.getGroup().getId());
        return eventDTO;
    }

    private Event mapToEntity(final EventDTO eventDTO, final Event event) {
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setMeetingType(eventDTO.getMeetingType());
        event.setMaxMember(eventDTO.getMaxMember());
        final Group group = eventDTO.getGroup() == null ? null : groupRepository.findById(eventDTO.getGroup())
                .orElseThrow(() -> new NotFoundException("group not found"));
        event.setGroup(group);
        return event;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Event event = eventRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final EventMember eventEventMember = eventMemberRepository.findFirstByEvent(event);
        if (eventEventMember != null) {
            referencedWarning.setKey("event.eventMember.event.referenced");
            referencedWarning.addParam(eventEventMember.getId());
            return referencedWarning;
        }
        final Schedule eventSchedule = scheduleRepository.findFirstByEvent(event);
        if (eventSchedule != null) {
            referencedWarning.setKey("event.schedule.event.referenced");
            referencedWarning.addParam(eventSchedule.getId());
            return referencedWarning;
        }
        final CandidateDate eventCandidateDate = candidateDateRepository.findFirstByEvent(event);
        if (eventCandidateDate != null) {
            referencedWarning.setKey("event.candidateDate.event.referenced");
            referencedWarning.addParam(eventCandidateDate.getId());
            return referencedWarning;
        }
        return null;
    }

}
