package com.grepp.spring.member.repos;

import com.grepp.spring.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, String> {

    boolean existsByUserIdIgnoreCase(String userId);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByPhoneNumberIgnoreCase(String phoneNumber);

}
