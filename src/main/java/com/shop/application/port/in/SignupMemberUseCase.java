package com.shop.application.port.in;

import com.shop.adapter.in.web.dto.input.MemberInput;
import com.shop.application.dto.MemberDto;

public interface SignupMemberUseCase {

    MemberDto signup(MemberInput input);

}
