package com.grepp.spring.app.model.favorite_timetable.service;

import com.grepp.spring.app.model.favorite_timetable.domain.FavoriteTimetable;
import com.grepp.spring.app.model.favorite_timetable.model.FavoriteTimetableDTO;
import com.grepp.spring.app.model.favorite_timetable.repos.FavoriteTimetableRepository;
import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.member.repos.MemberRepository;
import com.grepp.spring.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class FavoriteTimetableService {

    private final FavoriteTimetableRepository favoriteTimetableRepository;
    private final MemberRepository memberRepository;

    public FavoriteTimetableService(final FavoriteTimetableRepository favoriteTimetableRepository,
            final MemberRepository memberRepository) {
        this.favoriteTimetableRepository = favoriteTimetableRepository;
        this.memberRepository = memberRepository;
    }

    public List<FavoriteTimetableDTO> findAll() {
        final List<FavoriteTimetable> favoriteTimetables = favoriteTimetableRepository.findAll(Sort.by("id"));
        return favoriteTimetables.stream()
                .map(favoriteTimetable -> mapToDTO(favoriteTimetable, new FavoriteTimetableDTO()))
                .toList();
    }

    public FavoriteTimetableDTO get(final Long id) {
        return favoriteTimetableRepository.findById(id)
                .map(favoriteTimetable -> mapToDTO(favoriteTimetable, new FavoriteTimetableDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final FavoriteTimetableDTO favoriteTimetableDTO) {
        final FavoriteTimetable favoriteTimetable = new FavoriteTimetable();
        mapToEntity(favoriteTimetableDTO, favoriteTimetable);
        return favoriteTimetableRepository.save(favoriteTimetable).getId();
    }

    public void update(final Long id, final FavoriteTimetableDTO favoriteTimetableDTO) {
        final FavoriteTimetable favoriteTimetable = favoriteTimetableRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(favoriteTimetableDTO, favoriteTimetable);
        favoriteTimetableRepository.save(favoriteTimetable);
    }

    public void delete(final Long id) {
        favoriteTimetableRepository.deleteById(id);
    }

    private FavoriteTimetableDTO mapToDTO(final FavoriteTimetable favoriteTimetable,
            final FavoriteTimetableDTO favoriteTimetableDTO) {
        favoriteTimetableDTO.setId(favoriteTimetable.getId());
        favoriteTimetableDTO.setStartTime(favoriteTimetable.getStartTime());
        favoriteTimetableDTO.setEndTime(favoriteTimetable.getEndTime());
        favoriteTimetableDTO.setWeekday(favoriteTimetable.getWeekday());
        favoriteTimetableDTO.setMember(favoriteTimetable.getMember() == null ? null : favoriteTimetable.getMember().getId());
        return favoriteTimetableDTO;
    }

    private FavoriteTimetable mapToEntity(final FavoriteTimetableDTO favoriteTimetableDTO,
            final FavoriteTimetable favoriteTimetable) {
        favoriteTimetable.setStartTime(favoriteTimetableDTO.getStartTime());
        favoriteTimetable.setEndTime(favoriteTimetableDTO.getEndTime());
        favoriteTimetable.setWeekday(favoriteTimetableDTO.getWeekday());
        final Member member = favoriteTimetableDTO.getMember() == null ? null : memberRepository.findById(favoriteTimetableDTO.getMember())
                .orElseThrow(() -> new NotFoundException("member not found"));
        favoriteTimetable.setMember(member);
        return favoriteTimetable;
    }

}
