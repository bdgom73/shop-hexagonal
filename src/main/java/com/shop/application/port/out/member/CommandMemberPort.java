package com.shop.application.port.out.member;

import com.shop.domain.Member;

public interface CommandMemberPort {
    Member save(Member member);
    Member update(Long id, Member member);
}
