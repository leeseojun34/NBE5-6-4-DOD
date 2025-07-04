package com.grepp.spring.app.model.event_member.service;

import com.grepp.spring.app.model.event.domain.Event;
import com.grepp.spring.app.model.event.repos.EventRepository;
import com.grepp.spring.app.model.event_member.domain.EventMember;
import com.grepp.spring.app.model.event_member.model.EventMemberDTO;
import com.grepp.spring.app.model.event_member.repos.EventMemberRepository;
import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.member.repos.MemberRepository;
import com.grepp.spring.app.model.temp_schedule.domain.TempSchedule;
import com.grepp.spring.app.model.temp_schedule.repos.TempScheduleRepository;
import com.grepp.spring.util.NotFoundException;
import com.grepp.spring.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EventMemberService {

    private final EventMemberRepository eventMemberRepository;
    private final MemberRepository memberRepository;
    private final EventRepository eventRepository;
    private final TempScheduleRepository tempScheduleRepository;

    public EventMemberService(final EventMemberRepository eventMemberRepository,
            final MemberRepository memberRepository, final EventRepository eventRepository,
            final TempScheduleRepository tempScheduleRepository) {
        this.eventMemberRepository = eventMemberRepository;
        this.memberRepository = memberRepository;
        this.eventRepository = eventRepository;
        this.tempScheduleRepository = tempScheduleRepository;
    }

    public List<EventMemberDTO> findAll() {
        final List<EventMember> eventMembers = eventMemberRepository.findAll(Sort.by("id"));
        return eventMembers.stream()
                .map(eventMember -> mapToDTO(eventMember, new EventMemberDTO()))
                .toList();
    }

    public EventMemberDTO get(final Long id) {
        return eventMemberRepository.findById(id)
                .map(eventMember -> mapToDTO(eventMember, new EventMemberDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EventMemberDTO eventMemberDTO) {
        final EventMember eventMember = new EventMember();
        mapToEntity(eventMemberDTO, eventMember);
        return eventMemberRepository.save(eventMember).getId();
    }

    public void update(final Long id, final EventMemberDTO eventMemberDTO) {
        final EventMember eventMember = eventMemberRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(eventMemberDTO, eventMember);
        eventMemberRepository.save(eventMember);
    }

    public void delete(final Long id) {
        eventMemberRepository.deleteById(id);
    }

    private EventMemberDTO mapToDTO(final EventMember eventMember,
            final EventMemberDTO eventMemberDTO) {
        eventMemberDTO.setId(eventMember.getId());
        eventMemberDTO.setRole(eventMember.getRole());
        eventMemberDTO.setMember(eventMember.getMember() == null ? null : eventMember.getMember().getId());
        eventMemberDTO.setEvent(eventMember.getEvent() == null ? null : eventMember.getEvent().getId());
        return eventMemberDTO;
    }

    private EventMember mapToEntity(final EventMemberDTO eventMemberDTO,
            final EventMember eventMember) {
        eventMember.setRole(eventMemberDTO.getRole());
        final Member member = eventMemberDTO.getMember() == null ? null : memberRepository.findById(eventMemberDTO.getMember())
                .orElseThrow(() -> new NotFoundException("member not found"));
        eventMember.setMember(member);
        final Event event = eventMemberDTO.getEvent() == null ? null : eventRepository.findById(eventMemberDTO.getEvent())
                .orElseThrow(() -> new NotFoundException("event not found"));
        eventMember.setEvent(event);
        return eventMember;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final EventMember eventMember = eventMemberRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final TempSchedule eventMemberTempSchedule = tempScheduleRepository.findFirstByEventMember(eventMember);
        if (eventMemberTempSchedule != null) {
            referencedWarning.setKey("eventMember.tempSchedule.eventMember.referenced");
            referencedWarning.addParam(eventMemberTempSchedule.getId());
            return referencedWarning;
        }
        return null;
    }

}
