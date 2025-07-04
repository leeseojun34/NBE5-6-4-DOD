package com.grepp.spring.app.model.temp_schedule.service;

import com.grepp.spring.app.model.event_member.domain.EventMember;
import com.grepp.spring.app.model.event_member.repos.EventMemberRepository;
import com.grepp.spring.app.model.temp_schedule.domain.TempSchedule;
import com.grepp.spring.app.model.temp_schedule.model.TempScheduleDTO;
import com.grepp.spring.app.model.temp_schedule.repos.TempScheduleRepository;
import com.grepp.spring.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TempScheduleService {

    private final TempScheduleRepository tempScheduleRepository;
    private final EventMemberRepository eventMemberRepository;

    public TempScheduleService(final TempScheduleRepository tempScheduleRepository,
            final EventMemberRepository eventMemberRepository) {
        this.tempScheduleRepository = tempScheduleRepository;
        this.eventMemberRepository = eventMemberRepository;
    }

    public List<TempScheduleDTO> findAll() {
        final List<TempSchedule> tempSchedules = tempScheduleRepository.findAll(Sort.by("id"));
        return tempSchedules.stream()
                .map(tempSchedule -> mapToDTO(tempSchedule, new TempScheduleDTO()))
                .toList();
    }

    public TempScheduleDTO get(final Long id) {
        return tempScheduleRepository.findById(id)
                .map(tempSchedule -> mapToDTO(tempSchedule, new TempScheduleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TempScheduleDTO tempScheduleDTO) {
        final TempSchedule tempSchedule = new TempSchedule();
        mapToEntity(tempScheduleDTO, tempSchedule);
        return tempScheduleRepository.save(tempSchedule).getId();
    }

    public void update(final Long id, final TempScheduleDTO tempScheduleDTO) {
        final TempSchedule tempSchedule = tempScheduleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(tempScheduleDTO, tempSchedule);
        tempScheduleRepository.save(tempSchedule);
    }

    public void delete(final Long id) {
        tempScheduleRepository.deleteById(id);
    }

    private TempScheduleDTO mapToDTO(final TempSchedule tempSchedule,
            final TempScheduleDTO tempScheduleDTO) {
        tempScheduleDTO.setId(tempSchedule.getId());
        tempScheduleDTO.setStartTime(tempSchedule.getStartTime());
        tempScheduleDTO.setEndTime(tempSchedule.getEndTime());
        tempScheduleDTO.setEventMember(tempSchedule.getEventMember() == null ? null : tempSchedule.getEventMember().getId());
        return tempScheduleDTO;
    }

    private TempSchedule mapToEntity(final TempScheduleDTO tempScheduleDTO,
            final TempSchedule tempSchedule) {
        tempSchedule.setStartTime(tempScheduleDTO.getStartTime());
        tempSchedule.setEndTime(tempScheduleDTO.getEndTime());
        final EventMember eventMember = tempScheduleDTO.getEventMember() == null ? null : eventMemberRepository.findById(tempScheduleDTO.getEventMember())
                .orElseThrow(() -> new NotFoundException("eventMember not found"));
        tempSchedule.setEventMember(eventMember);
        return tempSchedule;
    }

}
