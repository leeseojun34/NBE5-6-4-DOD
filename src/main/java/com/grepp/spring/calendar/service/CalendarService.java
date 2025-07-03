package com.grepp.spring.calendar.service;

import com.grepp.spring.calendar.domain.Calendar;
import com.grepp.spring.calendar.model.CalendarDTO;
import com.grepp.spring.calendar.repos.CalendarRepository;
import com.grepp.spring.calendar_detail.domain.CalendarDetail;
import com.grepp.spring.calendar_detail.repos.CalendarDetailRepository;
import com.grepp.spring.member.domain.Member;
import com.grepp.spring.member.repos.MemberRepository;
import com.grepp.spring.util.NotFoundException;
import com.grepp.spring.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final MemberRepository memberRepository;
    private final CalendarDetailRepository calendarDetailRepository;

    public CalendarService(final CalendarRepository calendarRepository,
            final MemberRepository memberRepository,
            final CalendarDetailRepository calendarDetailRepository) {
        this.calendarRepository = calendarRepository;
        this.memberRepository = memberRepository;
        this.calendarDetailRepository = calendarDetailRepository;
    }

    public List<CalendarDTO> findAll() {
        final List<Calendar> calendars = calendarRepository.findAll(Sort.by("calendarId"));
        return calendars.stream()
                .map(calendar -> mapToDTO(calendar, new CalendarDTO()))
                .toList();
    }

    public CalendarDTO get(final Long calendarId) {
        return calendarRepository.findById(calendarId)
                .map(calendar -> mapToDTO(calendar, new CalendarDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CalendarDTO calendarDTO) {
        final Calendar calendar = new Calendar();
        mapToEntity(calendarDTO, calendar);
        return calendarRepository.save(calendar).getCalendarId();
    }

    public void update(final Long calendarId, final CalendarDTO calendarDTO) {
        final Calendar calendar = calendarRepository.findById(calendarId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(calendarDTO, calendar);
        calendarRepository.save(calendar);
    }

    public void delete(final Long calendarId) {
        calendarRepository.deleteById(calendarId);
    }

    private CalendarDTO mapToDTO(final Calendar calendar, final CalendarDTO calendarDTO) {
        calendarDTO.setCalendarId(calendar.getCalendarId());
        calendarDTO.setCalendarName(calendar.getCalendarName());
        calendarDTO.setSynced(calendar.getSynced());
        calendarDTO.setSyncedAt(calendar.getSyncedAt());
        calendarDTO.setUser(calendar.getUser() == null ? null : calendar.getUser().getUserId());
        return calendarDTO;
    }

    private Calendar mapToEntity(final CalendarDTO calendarDTO, final Calendar calendar) {
        calendar.setCalendarName(calendarDTO.getCalendarName());
        calendar.setSynced(calendarDTO.getSynced());
        calendar.setSyncedAt(calendarDTO.getSyncedAt());
        final Member user = calendarDTO.getUser() == null ? null : memberRepository.findById(calendarDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        calendar.setUser(user);
        return calendar;
    }

    public boolean userExists(final String userId) {
        return calendarRepository.existsByUserUserIdIgnoreCase(userId);
    }

    public ReferencedWarning getReferencedWarning(final Long calendarId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Calendar calendar = calendarRepository.findById(calendarId)
                .orElseThrow(NotFoundException::new);
        final CalendarDetail calendarCalendarDetail = calendarDetailRepository.findFirstByCalendar(calendar);
        if (calendarCalendarDetail != null) {
            referencedWarning.setKey("calendar.calendarDetail.calendar.referenced");
            referencedWarning.addParam(calendarCalendarDetail.getCalendarDetailId());
            return referencedWarning;
        }
        return null;
    }

}
