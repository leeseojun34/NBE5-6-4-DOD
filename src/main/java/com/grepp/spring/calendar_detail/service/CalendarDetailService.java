package com.grepp.spring.calendar_detail.service;

import com.grepp.spring.calendar.domain.Calendar;
import com.grepp.spring.calendar.repos.CalendarRepository;
import com.grepp.spring.calendar_detail.domain.CalendarDetail;
import com.grepp.spring.calendar_detail.model.CalendarDetailDTO;
import com.grepp.spring.calendar_detail.repos.CalendarDetailRepository;
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
        final List<CalendarDetail> calendarDetails = calendarDetailRepository.findAll(Sort.by("calendarDetailId"));
        return calendarDetails.stream()
                .map(calendarDetail -> mapToDTO(calendarDetail, new CalendarDetailDTO()))
                .toList();
    }

    public CalendarDetailDTO get(final Long calendarDetailId) {
        return calendarDetailRepository.findById(calendarDetailId)
                .map(calendarDetail -> mapToDTO(calendarDetail, new CalendarDetailDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CalendarDetailDTO calendarDetailDTO) {
        final CalendarDetail calendarDetail = new CalendarDetail();
        mapToEntity(calendarDetailDTO, calendarDetail);
        return calendarDetailRepository.save(calendarDetail).getCalendarDetailId();
    }

    public void update(final Long calendarDetailId, final CalendarDetailDTO calendarDetailDTO) {
        final CalendarDetail calendarDetail = calendarDetailRepository.findById(calendarDetailId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(calendarDetailDTO, calendarDetail);
        calendarDetailRepository.save(calendarDetail);
    }

    public void delete(final Long calendarDetailId) {
        calendarDetailRepository.deleteById(calendarDetailId);
    }

    private CalendarDetailDTO mapToDTO(final CalendarDetail calendarDetail,
            final CalendarDetailDTO calendarDetailDTO) {
        calendarDetailDTO.setCalendarDetailId(calendarDetail.getCalendarDetailId());
        calendarDetailDTO.setTitle(calendarDetail.getTitle());
        calendarDetailDTO.setStartDatetime(calendarDetail.getStartDatetime());
        calendarDetailDTO.setEndDatetime(calendarDetail.getEndDatetime());
        calendarDetailDTO.setSyncedAt(calendarDetail.getSyncedAt());
        calendarDetailDTO.setCalendar(calendarDetail.getCalendar() == null ? null : calendarDetail.getCalendar().getCalendarId());
        return calendarDetailDTO;
    }

    private CalendarDetail mapToEntity(final CalendarDetailDTO calendarDetailDTO,
            final CalendarDetail calendarDetail) {
        calendarDetail.setTitle(calendarDetailDTO.getTitle());
        calendarDetail.setStartDatetime(calendarDetailDTO.getStartDatetime());
        calendarDetail.setEndDatetime(calendarDetailDTO.getEndDatetime());
        calendarDetail.setSyncedAt(calendarDetailDTO.getSyncedAt());
        final Calendar calendar = calendarDetailDTO.getCalendar() == null ? null : calendarRepository.findById(calendarDetailDTO.getCalendar())
                .orElseThrow(() -> new NotFoundException("calendar not found"));
        calendarDetail.setCalendar(calendar);
        return calendarDetail;
    }

}
