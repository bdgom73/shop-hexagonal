package com.shop.application.dto;

import com.shop.adapter.out.constant.Role;
import com.shop.adapter.out.persistence.MemberEntity;
import com.shop.domain.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
public class MemberDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String address;
    private Role role;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.address = member.getAddress();
        this.role = member.getRole();
        this.regTime = member.getRegTime();
        this.updateTime = member.getUpdateTime();
    }
}
