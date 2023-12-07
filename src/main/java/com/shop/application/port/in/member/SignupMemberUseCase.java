package com.shop.application.port.in.member;

import com.shop.application.dto.MemberDto;
import com.shop.application.dto.request.MemberRequest;

public interface SignupMemberUseCase {

    MemberDto signup(MemberRequest request);

}
