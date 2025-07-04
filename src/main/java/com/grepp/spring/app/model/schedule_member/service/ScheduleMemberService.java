package com.grepp.spring.app.model.schedule_member.service;

import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.member.repos.MemberRepository;
import com.grepp.spring.app.model.middle_region.domain.MiddleRegion;
import com.grepp.spring.app.model.middle_region.repos.MiddleRegionRepository;
import com.grepp.spring.app.model.schedule.domain.Schedule;
import com.grepp.spring.app.model.schedule.repos.ScheduleRepository;
import com.grepp.spring.app.model.schedule_member.domain.ScheduleMember;
import com.grepp.spring.app.model.schedule_member.model.ScheduleMemberDTO;
import com.grepp.spring.app.model.schedule_member.repos.ScheduleMemberRepository;
import com.grepp.spring.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ScheduleMemberService {

    private final ScheduleMemberRepository scheduleMemberRepository;
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final MiddleRegionRepository middleRegionRepository;

    public ScheduleMemberService(final ScheduleMemberRepository scheduleMemberRepository,
            final MemberRepository memberRepository, final ScheduleRepository scheduleRepository,
            final MiddleRegionRepository middleRegionRepository) {
        this.scheduleMemberRepository = scheduleMemberRepository;
        this.memberRepository = memberRepository;
        this.scheduleRepository = scheduleRepository;
        this.middleRegionRepository = middleRegionRepository;
    }

    public List<ScheduleMemberDTO> findAll() {
        final List<ScheduleMember> scheduleMembers = scheduleMemberRepository.findAll(Sort.by("id"));
        return scheduleMembers.stream()
                .map(scheduleMember -> mapToDTO(scheduleMember, new ScheduleMemberDTO()))
                .toList();
    }

    public ScheduleMemberDTO get(final Long id) {
        return scheduleMemberRepository.findById(id)
                .map(scheduleMember -> mapToDTO(scheduleMember, new ScheduleMemberDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ScheduleMemberDTO scheduleMemberDTO) {
        final ScheduleMember scheduleMember = new ScheduleMember();
        mapToEntity(scheduleMemberDTO, scheduleMember);
        return scheduleMemberRepository.save(scheduleMember).getId();
    }

    public void update(final Long id, final ScheduleMemberDTO scheduleMemberDTO) {
        final ScheduleMember scheduleMember = scheduleMemberRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(scheduleMemberDTO, scheduleMember);
        scheduleMemberRepository.save(scheduleMember);
    }

    public void delete(final Long id) {
        scheduleMemberRepository.deleteById(id);
    }

    private ScheduleMemberDTO mapToDTO(final ScheduleMember scheduleMember,
            final ScheduleMemberDTO scheduleMemberDTO) {
        scheduleMemberDTO.setId(scheduleMember.getId());
        scheduleMemberDTO.setRole(scheduleMember.getRole());
        scheduleMemberDTO.setDepartLocationName(scheduleMember.getDepartLocationName());
        scheduleMemberDTO.setLatitude(scheduleMember.getLatitude());
        scheduleMemberDTO.setLongitude(scheduleMember.getLongitude());
        scheduleMemberDTO.setMember(scheduleMember.getMember() == null ? null : scheduleMember.getMember().getId());
        scheduleMemberDTO.setSchedule(scheduleMember.getSchedule() == null ? null : scheduleMember.getSchedule().getId());
        scheduleMemberDTO.setMiddleRegion(scheduleMember.getMiddleRegion() == null ? null : scheduleMember.getMiddleRegion().getId());
        return scheduleMemberDTO;
    }

    private ScheduleMember mapToEntity(final ScheduleMemberDTO scheduleMemberDTO,
            final ScheduleMember scheduleMember) {
        scheduleMember.setRole(scheduleMemberDTO.getRole());
        scheduleMember.setDepartLocationName(scheduleMemberDTO.getDepartLocationName());
        scheduleMember.setLatitude(scheduleMemberDTO.getLatitude());
        scheduleMember.setLongitude(scheduleMemberDTO.getLongitude());
        final Member member = scheduleMemberDTO.getMember() == null ? null : memberRepository.findById(scheduleMemberDTO.getMember())
                .orElseThrow(() -> new NotFoundException("member not found"));
        scheduleMember.setMember(member);
        final Schedule schedule = scheduleMemberDTO.getSchedule() == null ? null : scheduleRepository.findById(scheduleMemberDTO.getSchedule())
                .orElseThrow(() -> new NotFoundException("schedule not found"));
        scheduleMember.setSchedule(schedule);
        final MiddleRegion middleRegion = scheduleMemberDTO.getMiddleRegion() == null ? null : middleRegionRepository.findById(scheduleMemberDTO.getMiddleRegion())
                .orElseThrow(() -> new NotFoundException("middleRegion not found"));
        scheduleMember.setMiddleRegion(middleRegion);
        return scheduleMember;
    }

}
