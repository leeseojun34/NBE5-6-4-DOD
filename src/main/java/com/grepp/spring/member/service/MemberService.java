package com.grepp.spring.member.service;

import com.grepp.spring.calendar.domain.Calendar;
import com.grepp.spring.calendar.repos.CalendarRepository;
import com.grepp.spring.event_user.domain.EventUser;
import com.grepp.spring.event_user.repos.EventUserRepository;
import com.grepp.spring.group_user.domain.GroupUser;
import com.grepp.spring.group_user.repos.GroupUserRepository;
import com.grepp.spring.like_location.domain.LikeLocation;
import com.grepp.spring.like_location.repos.LikeLocationRepository;
import com.grepp.spring.like_timetable.domain.LikeTimetable;
import com.grepp.spring.like_timetable.repos.LikeTimetableRepository;
import com.grepp.spring.member.domain.Member;
import com.grepp.spring.member.domain.Role;
import com.grepp.spring.member.model.MemberDTO;
import com.grepp.spring.member.repos.MemberRepository;
import com.grepp.spring.schedule_user.domain.ScheduleUser;
import com.grepp.spring.schedule_user.repos.ScheduleUserRepository;
import com.grepp.spring.social_auth_tokens.domain.SocialAuthTokens;
import com.grepp.spring.social_auth_tokens.repos.SocialAuthTokensRepository;
import com.grepp.spring.util.NotFoundException;
import com.grepp.spring.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final SocialAuthTokensRepository socialAuthTokensRepository;
    private final LikeLocationRepository likeLocationRepository;
    private final LikeTimetableRepository likeTimetableRepository;
    private final CalendarRepository calendarRepository;
    private final GroupUserRepository groupUserRepository;
    private final EventUserRepository eventUserRepository;
    private final ScheduleUserRepository scheduleUserRepository;

    public MemberService(final MemberRepository memberRepository,
            final SocialAuthTokensRepository socialAuthTokensRepository,
            final LikeLocationRepository likeLocationRepository,
            final LikeTimetableRepository likeTimetableRepository,
            final CalendarRepository calendarRepository,
            final GroupUserRepository groupUserRepository,
            final EventUserRepository eventUserRepository,
            final ScheduleUserRepository scheduleUserRepository) {
        this.memberRepository = memberRepository;
        this.socialAuthTokensRepository = socialAuthTokensRepository;
        this.likeLocationRepository = likeLocationRepository;
        this.likeTimetableRepository = likeTimetableRepository;
        this.calendarRepository = calendarRepository;
        this.groupUserRepository = groupUserRepository;
        this.eventUserRepository = eventUserRepository;
        this.scheduleUserRepository = scheduleUserRepository;
    }

    public List<MemberDTO> findAll() {
        final List<Member> members = memberRepository.findAll(Sort.by("userId"));
        return members.stream()
                .map(member -> mapToDTO(member, new MemberDTO()))
                .toList();
    }

    public MemberDTO get(final String userId) {
        return memberRepository.findById(userId)
                .map(member -> mapToDTO(member, new MemberDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final MemberDTO memberDTO) {
        final Member member = new Member();
        mapToEntity(memberDTO, member);
        member.setUserId(memberDTO.getUserId());
        return memberRepository.save(member).getUserId();
    }

    public void update(final String userId, final MemberDTO memberDTO) {
        final Member member = memberRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(memberDTO, member);
        memberRepository.save(member);
    }

    public void delete(final String userId) {
        memberRepository.deleteById(userId);
    }

    private MemberDTO mapToDTO(final Member member, final MemberDTO memberDTO) {
        memberDTO.setUserId(member.getUserId());
        memberDTO.setPassword(member.getPassword());
        memberDTO.setProvider(member.getProvider());
        memberDTO.setRole(String.valueOf(member.getRole()));
        memberDTO.setEmail(member.getEmail());
        memberDTO.setName(member.getName());
        memberDTO.setProfileImageNumber(member.getProfileImageNumber());
        memberDTO.setPhoneNumber(member.getPhoneNumber());
        return memberDTO;
    }

    private Member mapToEntity(final MemberDTO memberDTO, final Member member) {
        member.setPassword(memberDTO.getPassword());
        member.setProvider(memberDTO.getProvider());
        member.setRole(Role.valueOf(memberDTO.getRole()));
        member.setEmail(memberDTO.getEmail());
        member.setName(memberDTO.getName());
        member.setProfileImageNumber(memberDTO.getProfileImageNumber());
        member.setPhoneNumber(memberDTO.getPhoneNumber());
        return member;
    }

    public boolean userIdExists(final String userId) {
        return memberRepository.existsByUserIdIgnoreCase(userId);
    }

    public boolean emailExists(final String email) {
        return memberRepository.existsByEmailIgnoreCase(email);
    }

    public boolean phoneNumberExists(final String phoneNumber) {
        return memberRepository.existsByPhoneNumberIgnoreCase(phoneNumber);
    }

    public ReferencedWarning getReferencedWarning(final String userId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Member member = memberRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        final SocialAuthTokens userSocialAuthTokens = socialAuthTokensRepository.findFirstByUser(member);
        if (userSocialAuthTokens != null) {
            referencedWarning.setKey("member.socialAuthTokens.user.referenced");
            referencedWarning.addParam(userSocialAuthTokens.getSocialAuthTokensId());
            return referencedWarning;
        }
        final LikeLocation userLikeLocation = likeLocationRepository.findFirstByUser(member);
        if (userLikeLocation != null) {
            referencedWarning.setKey("member.likeLocation.user.referenced");
            referencedWarning.addParam(userLikeLocation.getLikeLocationId());
            return referencedWarning;
        }
        final LikeTimetable userLikeTimetable = likeTimetableRepository.findFirstByUser(member);
        if (userLikeTimetable != null) {
            referencedWarning.setKey("member.likeTimetable.user.referenced");
            referencedWarning.addParam(userLikeTimetable.getLikeTimetableId());
            return referencedWarning;
        }
        final Calendar userCalendar = calendarRepository.findFirstByUser(member);
        if (userCalendar != null) {
            referencedWarning.setKey("member.calendar.user.referenced");
            referencedWarning.addParam(userCalendar.getCalendarId());
            return referencedWarning;
        }
        final GroupUser userGroupUser = groupUserRepository.findFirstByUser(member);
        if (userGroupUser != null) {
            referencedWarning.setKey("member.groupUser.user.referenced");
            referencedWarning.addParam(userGroupUser.getGroupUserId());
            return referencedWarning;
        }
        final EventUser userEventUser = eventUserRepository.findFirstByUser(member);
        if (userEventUser != null) {
            referencedWarning.setKey("member.eventUser.user.referenced");
            referencedWarning.addParam(userEventUser.getEventUserId());
            return referencedWarning;
        }
        final ScheduleUser userScheduleUser = scheduleUserRepository.findFirstByUser(member);
        if (userScheduleUser != null) {
            referencedWarning.setKey("member.scheduleUser.user.referenced");
            referencedWarning.addParam(userScheduleUser.getScheduleUserId());
            return referencedWarning;
        }
        return null;
    }

}
