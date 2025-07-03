package com.grepp.spring.schedule_user.service;

import com.grepp.spring.member.domain.Member;
import com.grepp.spring.member.repos.MemberRepository;
import com.grepp.spring.schedule.domain.Schedule;
import com.grepp.spring.schedule.repos.ScheduleRepository;
import com.grepp.spring.schedule_user.domain.ScheduleUser;
import com.grepp.spring.schedule_user.model.ScheduleUserDTO;
import com.grepp.spring.schedule_user.repos.ScheduleUserRepository;
import com.grepp.spring.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ScheduleUserService {

    private final ScheduleUserRepository scheduleUserRepository;
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleUserService(final ScheduleUserRepository scheduleUserRepository,
            final MemberRepository memberRepository, final ScheduleRepository scheduleRepository) {
        this.scheduleUserRepository = scheduleUserRepository;
        this.memberRepository = memberRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public List<ScheduleUserDTO> findAll() {
        final List<ScheduleUser> scheduleUsers = scheduleUserRepository.findAll(Sort.by("scheduleUserId"));
        return scheduleUsers.stream()
                .map(scheduleUser -> mapToDTO(scheduleUser, new ScheduleUserDTO()))
                .toList();
    }

    public ScheduleUserDTO get(final Long scheduleUserId) {
        return scheduleUserRepository.findById(scheduleUserId)
                .map(scheduleUser -> mapToDTO(scheduleUser, new ScheduleUserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ScheduleUserDTO scheduleUserDTO) {
        final ScheduleUser scheduleUser = new ScheduleUser();
        mapToEntity(scheduleUserDTO, scheduleUser);
        return scheduleUserRepository.save(scheduleUser).getScheduleUserId();
    }

    public void update(final Long scheduleUserId, final ScheduleUserDTO scheduleUserDTO) {
        final ScheduleUser scheduleUser = scheduleUserRepository.findById(scheduleUserId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(scheduleUserDTO, scheduleUser);
        scheduleUserRepository.save(scheduleUser);
    }

    public void delete(final Long scheduleUserId) {
        scheduleUserRepository.deleteById(scheduleUserId);
    }

    private ScheduleUserDTO mapToDTO(final ScheduleUser scheduleUser,
            final ScheduleUserDTO scheduleUserDTO) {
        scheduleUserDTO.setScheduleUserId(scheduleUser.getScheduleUserId());
        scheduleUserDTO.setRole(scheduleUser.getRole());
        scheduleUserDTO.setUser(scheduleUser.getUser() == null ? null : scheduleUser.getUser().getUserId());
        scheduleUserDTO.setSchedule(scheduleUser.getSchedule() == null ? null : scheduleUser.getSchedule().getScheduleId());
        return scheduleUserDTO;
    }

    private ScheduleUser mapToEntity(final ScheduleUserDTO scheduleUserDTO,
            final ScheduleUser scheduleUser) {
        scheduleUser.setRole(scheduleUserDTO.getRole());
        final Member user = scheduleUserDTO.getUser() == null ? null : memberRepository.findById(scheduleUserDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        scheduleUser.setUser(user);
        final Schedule schedule = scheduleUserDTO.getSchedule() == null ? null : scheduleRepository.findById(scheduleUserDTO.getSchedule())
                .orElseThrow(() -> new NotFoundException("schedule not found"));
        scheduleUser.setSchedule(schedule);
        return scheduleUser;
    }

}
