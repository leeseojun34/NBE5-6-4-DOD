package com.grepp.spring.app.model.schedule.service;

import com.grepp.spring.app.model.event.domain.Event;
import com.grepp.spring.app.model.event.repos.EventRepository;
import com.grepp.spring.app.model.schedule.domain.Schedule;
import com.grepp.spring.app.model.schedule.model.ScheduleDTO;
import com.grepp.spring.app.model.schedule.repos.ScheduleRepository;
import com.grepp.spring.app.model.schedule_member.domain.ScheduleMember;
import com.grepp.spring.app.model.schedule_member.repos.ScheduleMemberRepository;
import com.grepp.spring.util.NotFoundException;
import com.grepp.spring.util.ReferencedWarning;
import com.grepp.spring.app.model.workspace.domain.Workspace;
import com.grepp.spring.app.model.workspace.repos.WorkspaceRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final EventRepository eventRepository;
    private final ScheduleMemberRepository scheduleMemberRepository;
    private final WorkspaceRepository workspaceRepository;

    public ScheduleService(final ScheduleRepository scheduleRepository,
            final EventRepository eventRepository,
            final ScheduleMemberRepository scheduleMemberRepository,
            final WorkspaceRepository workspaceRepository) {
        this.scheduleRepository = scheduleRepository;
        this.eventRepository = eventRepository;
        this.scheduleMemberRepository = scheduleMemberRepository;
        this.workspaceRepository = workspaceRepository;
    }

    public List<ScheduleDTO> findAll() {
        final List<Schedule> schedules = scheduleRepository.findAll(Sort.by("id"));
        return schedules.stream()
                .map(schedule -> mapToDTO(schedule, new ScheduleDTO()))
                .toList();
    }

    public ScheduleDTO get(final Long id) {
        return scheduleRepository.findById(id)
                .map(schedule -> mapToDTO(schedule, new ScheduleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ScheduleDTO scheduleDTO) {
        final Schedule schedule = new Schedule();
        mapToEntity(scheduleDTO, schedule);
        return scheduleRepository.save(schedule).getId();
    }

    public void update(final Long id, final ScheduleDTO scheduleDTO) {
        final Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(scheduleDTO, schedule);
        scheduleRepository.save(schedule);
    }

    public void delete(final Long id) {
        scheduleRepository.deleteById(id);
    }

    private ScheduleDTO mapToDTO(final Schedule schedule, final ScheduleDTO scheduleDTO) {
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setStartTime(schedule.getStartTime());
        scheduleDTO.setEndTime(schedule.getEndTime());
        scheduleDTO.setStatus(schedule.getStatus());
        scheduleDTO.setLocation(schedule.getLocation());
        scheduleDTO.setDescription(schedule.getDescription());
        scheduleDTO.setMeetingPlatform(schedule.getMeetingPlatform());
        scheduleDTO.setPlatformUrl(schedule.getPlatformUrl());
        scheduleDTO.setSpecificLocation(schedule.getSpecificLocation());
        scheduleDTO.setEvent(schedule.getEvent() == null ? null : schedule.getEvent().getId());
        return scheduleDTO;
    }

    private Schedule mapToEntity(final ScheduleDTO scheduleDTO, final Schedule schedule) {
        schedule.setStartTime(scheduleDTO.getStartTime());
        schedule.setEndTime(scheduleDTO.getEndTime());
        schedule.setStatus(scheduleDTO.getStatus());
        schedule.setLocation(scheduleDTO.getLocation());
        schedule.setDescription(scheduleDTO.getDescription());
        schedule.setMeetingPlatform(scheduleDTO.getMeetingPlatform());
        schedule.setPlatformUrl(scheduleDTO.getPlatformUrl());
        schedule.setSpecificLocation(scheduleDTO.getSpecificLocation());
        final Event event = scheduleDTO.getEvent() == null ? null : eventRepository.findById(scheduleDTO.getEvent())
                .orElseThrow(() -> new NotFoundException("event not found"));
        schedule.setEvent(event);
        return schedule;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final ScheduleMember scheduleScheduleMember = scheduleMemberRepository.findFirstBySchedule(schedule);
        if (scheduleScheduleMember != null) {
            referencedWarning.setKey("schedule.scheduleMember.schedule.referenced");
            referencedWarning.addParam(scheduleScheduleMember.getId());
            return referencedWarning;
        }
        final Workspace scheduleWorkspace = workspaceRepository.findFirstBySchedule(schedule);
        if (scheduleWorkspace != null) {
            referencedWarning.setKey("schedule.workspace.schedule.referenced");
            referencedWarning.addParam(scheduleWorkspace.getId());
            return referencedWarning;
        }
        return null;
    }

}
