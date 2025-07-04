package com.grepp.spring.app.model.calendar.service;

import com.grepp.spring.app.model.calendar.domain.Calendar;
import com.grepp.spring.app.model.calendar.model.CalendarDTO;
import com.grepp.spring.app.model.calendar.repos.CalendarRepository;
import com.grepp.spring.app.model.calendar_detail.domain.CalendarDetail;
import com.grepp.spring.app.model.calendar_detail.repos.CalendarDetailRepository;
import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.member.repos.MemberRepository;
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
        final List<Calendar> calendars = calendarRepository.findAll(Sort.by("id"));
        return calendars.stream()
                .map(calendar -> mapToDTO(calendar, new CalendarDTO()))
                .toList();
    }

    public CalendarDTO get(final Long id) {
        return calendarRepository.findById(id)
                .map(calendar -> mapToDTO(calendar, new CalendarDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CalendarDTO calendarDTO) {
        final Calendar calendar = new Calendar();
        mapToEntity(calendarDTO, calendar);
        return calendarRepository.save(calendar).getId();
    }

    public void update(final Long id, final CalendarDTO calendarDTO) {
        final Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(calendarDTO, calendar);
        calendarRepository.save(calendar);
    }

    public void delete(final Long id) {
        calendarRepository.deleteById(id);
    }

    private CalendarDTO mapToDTO(final Calendar calendar, final CalendarDTO calendarDTO) {
        calendarDTO.setId(calendar.getId());
        calendarDTO.setName(calendar.getName());
        calendarDTO.setSynced(calendar.getSynced());
        calendarDTO.setSyncedAt(calendar.getSyncedAt());
        calendarDTO.setMember(calendar.getMember() == null ? null : calendar.getMember().getId());
        return calendarDTO;
    }

    private Calendar mapToEntity(final CalendarDTO calendarDTO, final Calendar calendar) {
        calendar.setName(calendarDTO.getName());
        calendar.setSynced(calendarDTO.getSynced());
        calendar.setSyncedAt(calendarDTO.getSyncedAt());
        final Member member = calendarDTO.getMember() == null ? null : memberRepository.findById(calendarDTO.getMember())
                .orElseThrow(() -> new NotFoundException("member not found"));
        calendar.setMember(member);
        return calendar;
    }

    public boolean memberExists(final String id) {
        return calendarRepository.existsByMemberIdIgnoreCase(id);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final CalendarDetail calendarCalendarDetail = calendarDetailRepository.findFirstByCalendar(calendar);
        if (calendarCalendarDetail != null) {
            referencedWarning.setKey("calendar.calendarDetail.calendar.referenced");
            referencedWarning.addParam(calendarCalendarDetail.getId());
            return referencedWarning;
        }
        return null;
    }

}
