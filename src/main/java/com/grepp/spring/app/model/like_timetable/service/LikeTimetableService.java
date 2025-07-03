package com.grepp.spring.app.model.like_timetable.service;

import com.grepp.spring.app.model.like_timetable.domain.LikeTimetable;
import com.grepp.spring.app.model.like_timetable.model.LikeTimetableDTO;
import com.grepp.spring.app.model.like_timetable.repos.LikeTimetableRepository;
import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.member.repos.MemberRepository;
import com.grepp.spring.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LikeTimetableService {

    private final LikeTimetableRepository likeTimetableRepository;
    private final MemberRepository memberRepository;

    public LikeTimetableService(final LikeTimetableRepository likeTimetableRepository,
            final MemberRepository memberRepository) {
        this.likeTimetableRepository = likeTimetableRepository;
        this.memberRepository = memberRepository;
    }

    public List<LikeTimetableDTO> findAll() {
        final List<LikeTimetable> likeTimetables = likeTimetableRepository.findAll(Sort.by("likeTimetableId"));
        return likeTimetables.stream()
                .map(likeTimetable -> mapToDTO(likeTimetable, new LikeTimetableDTO()))
                .toList();
    }

    public LikeTimetableDTO get(final Long likeTimetableId) {
        return likeTimetableRepository.findById(likeTimetableId)
                .map(likeTimetable -> mapToDTO(likeTimetable, new LikeTimetableDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LikeTimetableDTO likeTimetableDTO) {
        final LikeTimetable likeTimetable = new LikeTimetable();
        mapToEntity(likeTimetableDTO, likeTimetable);
        return likeTimetableRepository.save(likeTimetable).getLikeTimetableId();
    }

    public void update(final Long likeTimetableId, final LikeTimetableDTO likeTimetableDTO) {
        final LikeTimetable likeTimetable = likeTimetableRepository.findById(likeTimetableId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(likeTimetableDTO, likeTimetable);
        likeTimetableRepository.save(likeTimetable);
    }

    public void delete(final Long likeTimetableId) {
        likeTimetableRepository.deleteById(likeTimetableId);
    }

    private LikeTimetableDTO mapToDTO(final LikeTimetable likeTimetable,
            final LikeTimetableDTO likeTimetableDTO) {
        likeTimetableDTO.setLikeTimetableId(likeTimetable.getLikeTimetableId());
        likeTimetableDTO.setStartTime(likeTimetable.getStartTime());
        likeTimetableDTO.setEndTime(likeTimetable.getEndTime());
        likeTimetableDTO.setWeekday(likeTimetable.getWeekday());
        likeTimetableDTO.setUser(likeTimetable.getUser() == null ? null : likeTimetable.getUser().getUserId());
        return likeTimetableDTO;
    }

    private LikeTimetable mapToEntity(final LikeTimetableDTO likeTimetableDTO,
            final LikeTimetable likeTimetable) {
        likeTimetable.setStartTime(likeTimetableDTO.getStartTime());
        likeTimetable.setEndTime(likeTimetableDTO.getEndTime());
        likeTimetable.setWeekday(likeTimetableDTO.getWeekday());
        final Member user = likeTimetableDTO.getUser() == null ? null : memberRepository.findById(likeTimetableDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        likeTimetable.setUser(user);
        return likeTimetable;
    }

}
