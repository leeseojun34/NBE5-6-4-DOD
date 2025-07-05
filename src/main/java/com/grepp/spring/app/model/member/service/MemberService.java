package com.grepp.spring.app.model.member.service;

import com.grepp.spring.app.model.calendar.domain.Calendar;
import com.grepp.spring.app.model.event_member.domain.EventMember;
import com.grepp.spring.app.model.favorite_location.domain.FavoriteLocation;
import com.grepp.spring.app.model.favorite_timetable.domain.FavoriteTimetable;
import com.grepp.spring.app.model.group_member.domain.GroupMember;
import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.member.domain.Role;
import com.grepp.spring.app.model.member.model.MemberDTO;
import com.grepp.spring.app.model.member.repos.MemberRepository;
import com.grepp.spring.app.model.schedule_member.domain.ScheduleMember;
import com.grepp.spring.app.model.social_auth_tokens.domain.SocialAuthTokens;
import com.grepp.spring.util.NotFoundException;
import com.grepp.spring.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
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

}
