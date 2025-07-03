package com.grepp.spring.app.model.meeting.service;

import com.grepp.spring.app.model.depart_region.domain.DepartRegion;
import com.grepp.spring.app.model.depart_region.repos.DepartRegionRepository;
import com.grepp.spring.app.model.meeting.domain.Meeting;
import com.grepp.spring.app.model.meeting.model.MeetingDTO;
import com.grepp.spring.app.model.meeting.repos.MeetingRepository;
import com.grepp.spring.app.model.schedule.domain.Schedule;
import com.grepp.spring.app.model.schedule.repos.ScheduleRepository;
import com.grepp.spring.util.NotFoundException;
import com.grepp.spring.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final ScheduleRepository scheduleRepository;
    private final DepartRegionRepository departRegionRepository;

    public MeetingService(final MeetingRepository meetingRepository,
            final ScheduleRepository scheduleRepository,
            final DepartRegionRepository departRegionRepository) {
        this.meetingRepository = meetingRepository;
        this.scheduleRepository = scheduleRepository;
        this.departRegionRepository = departRegionRepository;
    }

    public List<MeetingDTO> findAll() {
        final List<Meeting> meetings = meetingRepository.findAll(Sort.by("meetingId"));
        return meetings.stream()
                .map(meeting -> mapToDTO(meeting, new MeetingDTO()))
                .toList();
    }

    public MeetingDTO get(final Long meetingId) {
        return meetingRepository.findById(meetingId)
                .map(meeting -> mapToDTO(meeting, new MeetingDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final MeetingDTO meetingDTO) {
        final Meeting meeting = new Meeting();
        mapToEntity(meetingDTO, meeting);
        return meetingRepository.save(meeting).getMeetingId();
    }

    public void update(final Long meetingId, final MeetingDTO meetingDTO) {
        final Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(meetingDTO, meeting);
        meetingRepository.save(meeting);
    }

    public void delete(final Long meetingId) {
        meetingRepository.deleteById(meetingId);
    }

    private MeetingDTO mapToDTO(final Meeting meeting, final MeetingDTO meetingDTO) {
        meetingDTO.setMeetingId(meeting.getMeetingId());
        meetingDTO.setMeetingPlatform(meeting.getMeetingPlatform());
        meetingDTO.setPlatformUrl(meeting.getPlatformUrl());
        meetingDTO.setSchedule(meeting.getSchedule() == null ? null : meeting.getSchedule().getScheduleId());
        return meetingDTO;
    }

    private Meeting mapToEntity(final MeetingDTO meetingDTO, final Meeting meeting) {
        meeting.setMeetingPlatform(meetingDTO.getMeetingPlatform());
        meeting.setPlatformUrl(meetingDTO.getPlatformUrl());
        final Schedule schedule = meetingDTO.getSchedule() == null ? null : scheduleRepository.findById(meetingDTO.getSchedule())
                .orElseThrow(() -> new NotFoundException("schedule not found"));
        meeting.setSchedule(schedule);
        return meeting;
    }

    public boolean scheduleExists(final Long scheduleId) {
        return meetingRepository.existsByScheduleScheduleId(scheduleId);
    }

    public ReferencedWarning getReferencedWarning(final Long meetingId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(NotFoundException::new);
        final DepartRegion meetingDepartRegion = departRegionRepository.findFirstByMeeting(meeting);
        if (meetingDepartRegion != null) {
            referencedWarning.setKey("meeting.departRegion.meeting.referenced");
            referencedWarning.addParam(meetingDepartRegion.getDepartRegionId());
            return referencedWarning;
        }
        return null;
    }

}
