package com.shop.application.dto.request;

import com.shop.adapter.out.constant.Role;
import com.shop.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class MemberRequest {

    private String name;
    private String email;
    private String password;
    private String address;

    @Builder
    public MemberRequest(String name, String email, String password, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public Member toDomain() {
        return Member.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .address(this.address)
                .role(Role.USER)
                .build();
    }
}
