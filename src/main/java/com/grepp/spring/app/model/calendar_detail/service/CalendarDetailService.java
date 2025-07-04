package com.grepp.spring.app.model.calendar_detail.service;

import com.grepp.spring.app.model.calendar.domain.Calendar;
import com.grepp.spring.app.model.calendar.repos.CalendarRepository;
import com.grepp.spring.app.model.calendar_detail.domain.CalendarDetail;
import com.grepp.spring.app.model.calendar_detail.model.CalendarDetailDTO;
import com.grepp.spring.app.model.calendar_detail.repos.CalendarDetailRepository;
import com.grepp.spring.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CalendarDetailService {

    private final CalendarDetailRepository calendarDetailRepository;
    private final CalendarRepository calendarRepository;

    public CalendarDetailService(final CalendarDetailRepository calendarDetailRepository,
            final CalendarRepository calendarRepository) {
        this.calendarDetailRepository = calendarDetailRepository;
        this.calendarRepository = calendarRepository;
    }

    public List<CalendarDetailDTO> findAll() {
        final List<CalendarDetail> calendarDetails = calendarDetailRepository.findAll(Sort.by("id"));
        return calendarDetails.stream()
                .map(calendarDetail -> mapToDTO(calendarDetail, new CalendarDetailDTO()))
                .toList();
    }

    public CalendarDetailDTO get(final Long id) {
        return calendarDetailRepository.findById(id)
                .map(calendarDetail -> mapToDTO(calendarDetail, new CalendarDetailDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CalendarDetailDTO calendarDetailDTO) {
        final CalendarDetail calendarDetail = new CalendarDetail();
        mapToEntity(calendarDetailDTO, calendarDetail);
        return calendarDetailRepository.save(calendarDetail).getId();
    }

    public void update(final Long id, final CalendarDetailDTO calendarDetailDTO) {
        final CalendarDetail calendarDetail = calendarDetailRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(calendarDetailDTO, calendarDetail);
        calendarDetailRepository.save(calendarDetail);
    }

    public void delete(final Long id) {
        calendarDetailRepository.deleteById(id);
    }

    private CalendarDetailDTO mapToDTO(final CalendarDetail calendarDetail,
            final CalendarDetailDTO calendarDetailDTO) {
        calendarDetailDTO.setId(calendarDetail.getId());
        calendarDetailDTO.setTitle(calendarDetail.getTitle());
        calendarDetailDTO.setStartDatetime(calendarDetail.getStartDatetime());
        calendarDetailDTO.setEndDatetime(calendarDetail.getEndDatetime());
        calendarDetailDTO.setSyncedAt(calendarDetail.getSyncedAt());
        calendarDetailDTO.setIsAllDay(calendarDetail.getIsAllDay());
        calendarDetailDTO.setExternalEtag(calendarDetail.getExternalEtag());
        calendarDetailDTO.setCalendar(calendarDetail.getCalendar() == null ? null : calendarDetail.getCalendar().getId());
        return calendarDetailDTO;
    }

    private CalendarDetail mapToEntity(final CalendarDetailDTO calendarDetailDTO,
            final CalendarDetail calendarDetail) {
        calendarDetail.setTitle(calendarDetailDTO.getTitle());
        calendarDetail.setStartDatetime(calendarDetailDTO.getStartDatetime());
        calendarDetail.setEndDatetime(calendarDetailDTO.getEndDatetime());
        calendarDetail.setSyncedAt(calendarDetailDTO.getSyncedAt());
        calendarDetail.setIsAllDay(calendarDetailDTO.getIsAllDay());
        calendarDetail.setExternalEtag(calendarDetailDTO.getExternalEtag());
        final Calendar calendar = calendarDetailDTO.getCalendar() == null ? null : calendarRepository.findById(calendarDetailDTO.getCalendar())
                .orElseThrow(() -> new NotFoundException("calendar not found"));
        calendarDetail.setCalendar(calendar);
        return calendarDetail;
    }

}
