package com.grepp.spring.event_user.service;

import com.grepp.spring.event.domain.Event;
import com.grepp.spring.event.repos.EventRepository;
import com.grepp.spring.event_user.domain.EventUser;
import com.grepp.spring.event_user.model.EventUserDTO;
import com.grepp.spring.event_user.repos.EventUserRepository;
import com.grepp.spring.member.domain.Member;
import com.grepp.spring.member.repos.MemberRepository;
import com.grepp.spring.temp_schedule.domain.TempSchedule;
import com.grepp.spring.temp_schedule.repos.TempScheduleRepository;
import com.grepp.spring.util.NotFoundException;
import com.grepp.spring.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EventUserService {

    private final EventUserRepository eventUserRepository;
    private final MemberRepository memberRepository;
    private final EventRepository eventRepository;
    private final TempScheduleRepository tempScheduleRepository;

    public EventUserService(final EventUserRepository eventUserRepository,
            final MemberRepository memberRepository, final EventRepository eventRepository,
            final TempScheduleRepository tempScheduleRepository) {
        this.eventUserRepository = eventUserRepository;
        this.memberRepository = memberRepository;
        this.eventRepository = eventRepository;
        this.tempScheduleRepository = tempScheduleRepository;
    }

    public List<EventUserDTO> findAll() {
        final List<EventUser> eventUsers = eventUserRepository.findAll(Sort.by("eventUserId"));
        return eventUsers.stream()
                .map(eventUser -> mapToDTO(eventUser, new EventUserDTO()))
                .toList();
    }

    public EventUserDTO get(final Long eventUserId) {
        return eventUserRepository.findById(eventUserId)
                .map(eventUser -> mapToDTO(eventUser, new EventUserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EventUserDTO eventUserDTO) {
        final EventUser eventUser = new EventUser();
        mapToEntity(eventUserDTO, eventUser);
        return eventUserRepository.save(eventUser).getEventUserId();
    }

    public void update(final Long eventUserId, final EventUserDTO eventUserDTO) {
        final EventUser eventUser = eventUserRepository.findById(eventUserId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(eventUserDTO, eventUser);
        eventUserRepository.save(eventUser);
    }

    public void delete(final Long eventUserId) {
        eventUserRepository.deleteById(eventUserId);
    }

    private EventUserDTO mapToDTO(final EventUser eventUser, final EventUserDTO eventUserDTO) {
        eventUserDTO.setEventUserId(eventUser.getEventUserId());
        eventUserDTO.setUser(eventUser.getUser() == null ? null : eventUser.getUser().getUserId());
        eventUserDTO.setEvent(eventUser.getEvent() == null ? null : eventUser.getEvent().getEventId());
        return eventUserDTO;
    }

    private EventUser mapToEntity(final EventUserDTO eventUserDTO, final EventUser eventUser) {
        final Member user = eventUserDTO.getUser() == null ? null : memberRepository.findById(eventUserDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        eventUser.setUser(user);
        final Event event = eventUserDTO.getEvent() == null ? null : eventRepository.findById(eventUserDTO.getEvent())
                .orElseThrow(() -> new NotFoundException("event not found"));
        eventUser.setEvent(event);
        return eventUser;
    }

    public ReferencedWarning getReferencedWarning(final Long eventUserId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final EventUser eventUser = eventUserRepository.findById(eventUserId)
                .orElseThrow(NotFoundException::new);
        final TempSchedule eventUserTempSchedule = tempScheduleRepository.findFirstByEventUser(eventUser);
        if (eventUserTempSchedule != null) {
            referencedWarning.setKey("eventUser.tempSchedule.eventUser.referenced");
            referencedWarning.addParam(eventUserTempSchedule.getTempScheduleId());
            return referencedWarning;
        }
        return null;
    }

}
