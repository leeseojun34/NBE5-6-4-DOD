package com.grepp.spring.schedule.service;

import com.grepp.spring.detail.domain.Detail;
import com.grepp.spring.detail.repos.DetailRepository;
import com.grepp.spring.event.domain.Event;
import com.grepp.spring.event.repos.EventRepository;
import com.grepp.spring.meeting.domain.Meeting;
import com.grepp.spring.meeting.repos.MeetingRepository;
import com.grepp.spring.schedule.domain.Schedule;
import com.grepp.spring.schedule.model.ScheduleDTO;
import com.grepp.spring.schedule.repos.ScheduleRepository;
import com.grepp.spring.schedule_user.domain.ScheduleUser;
import com.grepp.spring.schedule_user.repos.ScheduleUserRepository;
import com.grepp.spring.util.NotFoundException;
import com.grepp.spring.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final EventRepository eventRepository;
    private final ScheduleUserRepository scheduleUserRepository;
    private final DetailRepository detailRepository;
    private final MeetingRepository meetingRepository;

    public ScheduleService(final ScheduleRepository scheduleRepository,
            final EventRepository eventRepository,
            final ScheduleUserRepository scheduleUserRepository,
            final DetailRepository detailRepository, final MeetingRepository meetingRepository) {
        this.scheduleRepository = scheduleRepository;
        this.eventRepository = eventRepository;
        this.scheduleUserRepository = scheduleUserRepository;
        this.detailRepository = detailRepository;
        this.meetingRepository = meetingRepository;
    }

    public List<ScheduleDTO> findAll() {
        final List<Schedule> schedules = scheduleRepository.findAll(Sort.by("scheduleId"));
        return schedules.stream()
                .map(schedule -> mapToDTO(schedule, new ScheduleDTO()))
                .toList();
    }

    public ScheduleDTO get(final Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .map(schedule -> mapToDTO(schedule, new ScheduleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ScheduleDTO scheduleDTO) {
        final Schedule schedule = new Schedule();
        mapToEntity(scheduleDTO, schedule);
        return scheduleRepository.save(schedule).getScheduleId();
    }

    public void update(final Long scheduleId, final ScheduleDTO scheduleDTO) {
        final Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(scheduleDTO, schedule);
        scheduleRepository.save(schedule);
    }

    public void delete(final Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    private ScheduleDTO mapToDTO(final Schedule schedule, final ScheduleDTO scheduleDTO) {
        scheduleDTO.setScheduleId(schedule.getScheduleId());
        scheduleDTO.setStartTime(schedule.getStartTime());
        scheduleDTO.setEndTime(schedule.getEndTime());
        scheduleDTO.setStatus(schedule.getStatus());
        scheduleDTO.setEvent(schedule.getEvent() == null ? null : schedule.getEvent().getEventId());
        return scheduleDTO;
    }

    private Schedule mapToEntity(final ScheduleDTO scheduleDTO, final Schedule schedule) {
        schedule.setStartTime(scheduleDTO.getStartTime());
        schedule.setEndTime(scheduleDTO.getEndTime());
        schedule.setStatus(scheduleDTO.getStatus());
        final Event event = scheduleDTO.getEvent() == null ? null : eventRepository.findById(scheduleDTO.getEvent())
                .orElseThrow(() -> new NotFoundException("event not found"));
        schedule.setEvent(event);
        return schedule;
    }

    public ReferencedWarning getReferencedWarning(final Long scheduleId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(NotFoundException::new);
        final ScheduleUser scheduleScheduleUser = scheduleUserRepository.findFirstBySchedule(schedule);
        if (scheduleScheduleUser != null) {
            referencedWarning.setKey("schedule.scheduleUser.schedule.referenced");
            referencedWarning.addParam(scheduleScheduleUser.getScheduleUserId());
            return referencedWarning;
        }
        final Detail scheduleDetail = detailRepository.findFirstBySchedule(schedule);
        if (scheduleDetail != null) {
            referencedWarning.setKey("schedule.detail.schedule.referenced");
            referencedWarning.addParam(scheduleDetail.getDetailId());
            return referencedWarning;
        }
        final Meeting scheduleMeeting = meetingRepository.findFirstBySchedule(schedule);
        if (scheduleMeeting != null) {
            referencedWarning.setKey("schedule.meeting.schedule.referenced");
            referencedWarning.addParam(scheduleMeeting.getMeetingId());
            return referencedWarning;
        }
        return null;
    }

}
