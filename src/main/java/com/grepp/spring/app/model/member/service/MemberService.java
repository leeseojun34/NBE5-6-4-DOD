package com.grepp.spring.app.model.member.service;

import com.grepp.spring.app.model.calendar.domain.Calendar;
import com.grepp.spring.app.model.calendar.repos.CalendarRepository;
import com.grepp.spring.app.model.event_member.domain.EventMember;
import com.grepp.spring.app.model.event_member.repos.EventMemberRepository;
import com.grepp.spring.app.model.favorite_location.domain.FavoriteLocation;
import com.grepp.spring.app.model.favorite_location.repos.FavoriteLocationRepository;
import com.grepp.spring.app.model.favorite_timetable.domain.FavoriteTimetable;
import com.grepp.spring.app.model.favorite_timetable.repos.FavoriteTimetableRepository;
import com.grepp.spring.app.model.group_member.domain.GroupMember;
import com.grepp.spring.app.model.group_member.repos.GroupMemberRepository;
import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.member.domain.Role;
import com.grepp.spring.app.model.member.model.MemberDTO;
import com.grepp.spring.app.model.member.repos.MemberRepository;
import com.grepp.spring.app.model.schedule_member.domain.ScheduleMember;
import com.grepp.spring.app.model.schedule_member.repos.ScheduleMemberRepository;
import com.grepp.spring.app.model.social_auth_tokens.domain.SocialAuthTokens;
import com.grepp.spring.app.model.social_auth_tokens.repos.SocialAuthTokensRepository;
import com.grepp.spring.util.NotFoundException;
import com.grepp.spring.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final SocialAuthTokensRepository socialAuthTokensRepository;
    private final FavoriteLocationRepository favoriteLocationRepository;
    private final FavoriteTimetableRepository favoriteTimetableRepository;
    private final CalendarRepository calendarRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final EventMemberRepository eventMemberRepository;
    private final ScheduleMemberRepository scheduleMemberRepository;

    public MemberService(final MemberRepository memberRepository,
            final SocialAuthTokensRepository socialAuthTokensRepository,
            final FavoriteLocationRepository favoriteLocationRepository,
            final FavoriteTimetableRepository favoriteTimetableRepository,
            final CalendarRepository calendarRepository,
            final GroupMemberRepository groupMemberRepository,
            final EventMemberRepository eventMemberRepository,
            final ScheduleMemberRepository scheduleMemberRepository) {
        this.memberRepository = memberRepository;
        this.socialAuthTokensRepository = socialAuthTokensRepository;
        this.favoriteLocationRepository = favoriteLocationRepository;
        this.favoriteTimetableRepository = favoriteTimetableRepository;
        this.calendarRepository = calendarRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.eventMemberRepository = eventMemberRepository;
        this.scheduleMemberRepository = scheduleMemberRepository;
    }

    public List<MemberDTO> findAll() {
        final List<Member> members = memberRepository.findAll(Sort.by("id"));
        return members.stream()
                .map(member -> mapToDTO(member, new MemberDTO()))
                .toList();
    }

    public MemberDTO get(final String id) {
        return memberRepository.findById(id)
                .map(member -> mapToDTO(member, new MemberDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final MemberDTO memberDTO) {
        final Member member = new Member();
        mapToEntity(memberDTO, member);
        member.setId(memberDTO.getId());
        return memberRepository.save(member).getId();
    }

    public void update(final String id, final MemberDTO memberDTO) {
        final Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(memberDTO, member);
        memberRepository.save(member);
    }

    public void delete(final String id) {
        memberRepository.deleteById(id);
    }

    private MemberDTO mapToDTO(final Member member, final MemberDTO memberDTO) {
        memberDTO.setId(member.getId());
        memberDTO.setPassword(member.getPassword());
        memberDTO.setProvider(member.getProvider());
        memberDTO.setRole(String.valueOf(member.getRole()));
        memberDTO.setEmail(member.getEmail());
        memberDTO.setName(member.getName());
        memberDTO.setProfileImageNumber(member.getProfileImageNumber());
        memberDTO.setTel(member.getTel());
        return memberDTO;
    }

    private Member mapToEntity(final MemberDTO memberDTO, final Member member) {
        member.setPassword(memberDTO.getPassword());
        member.setProvider(memberDTO.getProvider());
        member.setRole(Role.valueOf(memberDTO.getRole()));
        member.setEmail(memberDTO.getEmail());
        member.setName(memberDTO.getName());
        member.setProfileImageNumber(memberDTO.getProfileImageNumber());
        member.setTel(memberDTO.getTel());
        return member;
    }

    public boolean idExists(final String id) {
        return memberRepository.existsByIdIgnoreCase(id);
    }

    public boolean emailExists(final String email) {
        return memberRepository.existsByEmailIgnoreCase(email);
    }

    public boolean telExists(final String tel) {
        return memberRepository.existsByTelIgnoreCase(tel);
    }

    public ReferencedWarning getReferencedWarning(final String id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final SocialAuthTokens memberSocialAuthTokens = socialAuthTokensRepository.findFirstByMember(member);
        if (memberSocialAuthTokens != null) {
            referencedWarning.setKey("member.socialAuthTokens.member.referenced");
            referencedWarning.addParam(memberSocialAuthTokens.getId());
            return referencedWarning;
        }
        final FavoriteLocation memberFavoriteLocation = favoriteLocationRepository.findFirstByMember(member);
        if (memberFavoriteLocation != null) {
            referencedWarning.setKey("member.favoriteLocation.member.referenced");
            referencedWarning.addParam(memberFavoriteLocation.getId());
            return referencedWarning;
        }
        final FavoriteTimetable memberFavoriteTimetable = favoriteTimetableRepository.findFirstByMember(member);
        if (memberFavoriteTimetable != null) {
            referencedWarning.setKey("member.favoriteTimetable.member.referenced");
            referencedWarning.addParam(memberFavoriteTimetable.getId());
            return referencedWarning;
        }
        final Calendar memberCalendar = calendarRepository.findFirstByMember(member);
        if (memberCalendar != null) {
            referencedWarning.setKey("member.calendar.member.referenced");
            referencedWarning.addParam(memberCalendar.getId());
            return referencedWarning;
        }
        final GroupMember memberGroupMember = groupMemberRepository.findFirstByMember(member);
        if (memberGroupMember != null) {
            referencedWarning.setKey("member.groupMember.member.referenced");
            referencedWarning.addParam(memberGroupMember.getId());
            return referencedWarning;
        }
        final EventMember memberEventMember = eventMemberRepository.findFirstByMember(member);
        if (memberEventMember != null) {
            referencedWarning.setKey("member.eventMember.member.referenced");
            referencedWarning.addParam(memberEventMember.getId());
            return referencedWarning;
        }
        final ScheduleMember memberScheduleMember = scheduleMemberRepository.findFirstByMember(member);
        if (memberScheduleMember != null) {
            referencedWarning.setKey("member.scheduleMember.member.referenced");
            referencedWarning.addParam(memberScheduleMember.getId());
            return referencedWarning;
        }
        return null;
    }

}
