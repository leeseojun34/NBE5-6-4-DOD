package com.grepp.spring.app.model.social_auth_tokens.service;

import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.member.repos.MemberRepository;
import com.grepp.spring.app.model.social_auth_tokens.domain.SocialAuthTokens;
import com.grepp.spring.app.model.social_auth_tokens.model.SocialAuthTokensDTO;
import com.grepp.spring.app.model.social_auth_tokens.repos.SocialAuthTokensRepository;
import com.grepp.spring.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SocialAuthTokensService {

    private final SocialAuthTokensRepository socialAuthTokensRepository;
    private final MemberRepository memberRepository;

    public SocialAuthTokensService(final SocialAuthTokensRepository socialAuthTokensRepository,
            final MemberRepository memberRepository) {
        this.socialAuthTokensRepository = socialAuthTokensRepository;
        this.memberRepository = memberRepository;
    }

    public List<SocialAuthTokensDTO> findAll() {
        final List<SocialAuthTokens> socialAuthTokenses = socialAuthTokensRepository.findAll(Sort.by("id"));
        return socialAuthTokenses.stream()
                .map(socialAuthTokens -> mapToDTO(socialAuthTokens, new SocialAuthTokensDTO()))
                .toList();
    }

    public SocialAuthTokensDTO get(final Long id) {
        return socialAuthTokensRepository.findById(id)
                .map(socialAuthTokens -> mapToDTO(socialAuthTokens, new SocialAuthTokensDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SocialAuthTokensDTO socialAuthTokensDTO) {
        final SocialAuthTokens socialAuthTokens = new SocialAuthTokens();
        mapToEntity(socialAuthTokensDTO, socialAuthTokens);
        return socialAuthTokensRepository.save(socialAuthTokens).getId();
    }

    public void update(final Long id, final SocialAuthTokensDTO socialAuthTokensDTO) {
        final SocialAuthTokens socialAuthTokens = socialAuthTokensRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(socialAuthTokensDTO, socialAuthTokens);
        socialAuthTokensRepository.save(socialAuthTokens);
    }

    public void delete(final Long id) {
        socialAuthTokensRepository.deleteById(id);
    }

    private SocialAuthTokensDTO mapToDTO(final SocialAuthTokens socialAuthTokens,
            final SocialAuthTokensDTO socialAuthTokensDTO) {
        socialAuthTokensDTO.setId(socialAuthTokens.getId());
        socialAuthTokensDTO.setAccessToken(socialAuthTokens.getAccessToken());
        socialAuthTokensDTO.setRefreshToken(socialAuthTokens.getRefreshToken());
        socialAuthTokensDTO.setTokenType(socialAuthTokens.getTokenType());
        socialAuthTokensDTO.setExpiresAt(socialAuthTokens.getExpiresAt());
        socialAuthTokensDTO.setProvider(socialAuthTokens.getProvider());
        socialAuthTokensDTO.setMember(socialAuthTokens.getMember() == null ? null : socialAuthTokens.getMember().getId());
        return socialAuthTokensDTO;
    }

    private SocialAuthTokens mapToEntity(final SocialAuthTokensDTO socialAuthTokensDTO,
            final SocialAuthTokens socialAuthTokens) {
        socialAuthTokens.setAccessToken(socialAuthTokensDTO.getAccessToken());
        socialAuthTokens.setRefreshToken(socialAuthTokensDTO.getRefreshToken());
        socialAuthTokens.setTokenType(socialAuthTokensDTO.getTokenType());
        socialAuthTokens.setExpiresAt(socialAuthTokensDTO.getExpiresAt());
        socialAuthTokens.setProvider(socialAuthTokensDTO.getProvider());
        final Member member = socialAuthTokensDTO.getMember() == null ? null : memberRepository.findById(socialAuthTokensDTO.getMember())
                .orElseThrow(() -> new NotFoundException("member not found"));
        socialAuthTokens.setMember(member);
        return socialAuthTokens;
    }

    public boolean accessTokenExists(final String accessToken) {
        return socialAuthTokensRepository.existsByAccessTokenIgnoreCase(accessToken);
    }

    public boolean refreshTokenExists(final String refreshToken) {
        return socialAuthTokensRepository.existsByRefreshTokenIgnoreCase(refreshToken);
    }

}
