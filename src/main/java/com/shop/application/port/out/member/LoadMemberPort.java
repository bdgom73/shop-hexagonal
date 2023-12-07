package com.shop.application.port.out.member;

import com.shop.domain.Member;

import java.util.Optional;

public interface LoadMemberPort {
    Optional<Member> loadById(Long id);
    Optional<Member> loadByEmail(String email);
}
