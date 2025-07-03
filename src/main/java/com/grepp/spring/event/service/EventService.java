package com.grepp.spring.event.service;

import com.grepp.spring.candidate_date.domain.CandidateDate;
import com.grepp.spring.candidate_date.repos.CandidateDateRepository;
import com.grepp.spring.event.domain.Event;
import com.grepp.spring.event.model.EventDTO;
import com.grepp.spring.event.repos.EventRepository;
import com.grepp.spring.event_user.domain.EventUser;
import com.grepp.spring.event_user.repos.EventUserRepository;
import com.grepp.spring.group.domain.Group;
import com.grepp.spring.group.repos.GroupRepository;
import com.grepp.spring.schedule.domain.Schedule;
import com.grepp.spring.schedule.repos.ScheduleRepository;
import com.grepp.spring.util.NotFoundException;
import com.grepp.spring.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EventService {

    private final EventRepository eventRepository;
    private final GroupRepository groupRepository;
    private final EventUserRepository eventUserRepository;
    private final CandidateDateRepository candidateDateRepository;
    private final ScheduleRepository scheduleRepository;

    public EventService(final EventRepository eventRepository,
            final GroupRepository groupRepository, final EventUserRepository eventUserRepository,
            final CandidateDateRepository candidateDateRepository,
            final ScheduleRepository scheduleRepository) {
        this.eventRepository = eventRepository;
        this.groupRepository = groupRepository;
        this.eventUserRepository = eventUserRepository;
        this.candidateDateRepository = candidateDateRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public List<EventDTO> findAll() {
        final List<Event> events = eventRepository.findAll(Sort.by("eventId"));
        return events.stream()
                .map(event -> mapToDTO(event, new EventDTO()))
                .toList();
    }

    public EventDTO get(final Long eventId) {
        return eventRepository.findById(eventId)
                .map(event -> mapToDTO(event, new EventDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EventDTO eventDTO) {
        final Event event = new Event();
        mapToEntity(eventDTO, event);
        return eventRepository.save(event).getEventId();
    }

    public void update(final Long eventId, final EventDTO eventDTO) {
        final Event event = eventRepository.findById(eventId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(eventDTO, event);
        eventRepository.save(event);
    }

    public void delete(final Long eventId) {
        eventRepository.deleteById(eventId);
    }

    private EventDTO mapToDTO(final Event event, final EventDTO eventDTO) {
        eventDTO.setEventId(event.getEventId());
        eventDTO.setTitle(event.getTitle());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setCreator(event.getCreator());
        eventDTO.setMeetingType(event.getMeetingType());
        eventDTO.setMaxMember(event.getMaxMember());
        eventDTO.setGroup(event.getGroup() == null ? null : event.getGroup().getGroupId());
        return eventDTO;
    }

    private Event mapToEntity(final EventDTO eventDTO, final Event event) {
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setCreator(eventDTO.getCreator());
        event.setMeetingType(eventDTO.getMeetingType());
        event.setMaxMember(eventDTO.getMaxMember());
        final Group group = eventDTO.getGroup() == null ? null : groupRepository.findById(eventDTO.getGroup())
                .orElseThrow(() -> new NotFoundException("group not found"));
        event.setGroup(group);
        return event;
    }

    public ReferencedWarning getReferencedWarning(final Long eventId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Event event = eventRepository.findById(eventId)
                .orElseThrow(NotFoundException::new);
        final EventUser eventEventUser = eventUserRepository.findFirstByEvent(event);
        if (eventEventUser != null) {
            referencedWarning.setKey("event.eventUser.event.referenced");
            referencedWarning.addParam(eventEventUser.getEventUserId());
            return referencedWarning;
        }
        final CandidateDate eventCandidateDate = candidateDateRepository.findFirstByEvent(event);
        if (eventCandidateDate != null) {
            referencedWarning.setKey("event.candidateDate.event.referenced");
            referencedWarning.addParam(eventCandidateDate.getCandidateDateId());
            return referencedWarning;
        }
        final Schedule eventSchedule = scheduleRepository.findFirstByEvent(event);
        if (eventSchedule != null) {
            referencedWarning.setKey("event.schedule.event.referenced");
            referencedWarning.addParam(eventSchedule.getScheduleId());
            return referencedWarning;
        }
        return null;
    }

}
