package com.shop.application.port.out;

import com.shop.adapter.in.web.dto.input.MemberInput;
import com.shop.domain.Member;

import java.util.Optional;

public interface CommandMemberPort {
    Member save(Member member);
    Member update(Long id, Member member);
}
