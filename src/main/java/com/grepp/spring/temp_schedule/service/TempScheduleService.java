package com.grepp.spring.temp_schedule.service;

import com.grepp.spring.event_user.domain.EventUser;
import com.grepp.spring.event_user.repos.EventUserRepository;
import com.grepp.spring.temp_schedule.domain.TempSchedule;
import com.grepp.spring.temp_schedule.model.TempScheduleDTO;
import com.grepp.spring.temp_schedule.repos.TempScheduleRepository;
import com.grepp.spring.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TempScheduleService {

    private final TempScheduleRepository tempScheduleRepository;
    private final EventUserRepository eventUserRepository;

    public TempScheduleService(final TempScheduleRepository tempScheduleRepository,
            final EventUserRepository eventUserRepository) {
        this.tempScheduleRepository = tempScheduleRepository;
        this.eventUserRepository = eventUserRepository;
    }

    public List<TempScheduleDTO> findAll() {
        final List<TempSchedule> tempSchedules = tempScheduleRepository.findAll(Sort.by("tempScheduleId"));
        return tempSchedules.stream()
                .map(tempSchedule -> mapToDTO(tempSchedule, new TempScheduleDTO()))
                .toList();
    }

    public TempScheduleDTO get(final Long tempScheduleId) {
        return tempScheduleRepository.findById(tempScheduleId)
                .map(tempSchedule -> mapToDTO(tempSchedule, new TempScheduleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TempScheduleDTO tempScheduleDTO) {
        final TempSchedule tempSchedule = new TempSchedule();
        mapToEntity(tempScheduleDTO, tempSchedule);
        return tempScheduleRepository.save(tempSchedule).getTempScheduleId();
    }

    public void update(final Long tempScheduleId, final TempScheduleDTO tempScheduleDTO) {
        final TempSchedule tempSchedule = tempScheduleRepository.findById(tempScheduleId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(tempScheduleDTO, tempSchedule);
        tempScheduleRepository.save(tempSchedule);
    }

    public void delete(final Long tempScheduleId) {
        tempScheduleRepository.deleteById(tempScheduleId);
    }

    private TempScheduleDTO mapToDTO(final TempSchedule tempSchedule,
            final TempScheduleDTO tempScheduleDTO) {
        tempScheduleDTO.setTempScheduleId(tempSchedule.getTempScheduleId());
        tempScheduleDTO.setStartTime(tempSchedule.getStartTime());
        tempScheduleDTO.setEndTime(tempSchedule.getEndTime());
        tempScheduleDTO.setEventUser(tempSchedule.getEventUser() == null ? null : tempSchedule.getEventUser().getEventUserId());
        return tempScheduleDTO;
    }

    private TempSchedule mapToEntity(final TempScheduleDTO tempScheduleDTO,
            final TempSchedule tempSchedule) {
        tempSchedule.setStartTime(tempScheduleDTO.getStartTime());
        tempSchedule.setEndTime(tempScheduleDTO.getEndTime());
        final EventUser eventUser = tempScheduleDTO.getEventUser() == null ? null : eventUserRepository.findById(tempScheduleDTO.getEventUser())
                .orElseThrow(() -> new NotFoundException("eventUser not found"));
        tempSchedule.setEventUser(eventUser);
        return tempSchedule;
    }

}
