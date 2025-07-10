package com.grepp.spring.app.model.mypage.entity;

import com.grepp.spring.app.model.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "FavoriteTimetables")
@Getter
@Setter
public class FavoriteTimetable {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private LocalTime startTime; // LocalDateTime -> LocalTime 으로 변경

    @Column(nullable = false)
    private LocalTime endTime; // LocalDateTime -> LocalTime 으로 변경

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // DB 에 문자로 저장되게 추가 (ex. "MONDAY")
    private DayOfWeek weekday; // String -> DayOfWeek 타입으로 변경

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
