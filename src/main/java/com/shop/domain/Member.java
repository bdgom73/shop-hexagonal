package com.shop.domain;

import com.shop.adapter.out.constant.Role;
import com.shop.adapter.out.persistence.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
public class Member {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String address;
    private Role role;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    public Member(MemberEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.address = entity.getAddress();
        this.role = entity.getRole();
        this.regTime = entity.getRegTime();
        this.updateTime = entity.getUpdateTime();
    }

    @Builder
    public Member(Long id, String name, String email, String password, String address, Role role, LocalDateTime regTime, LocalDateTime updateTime) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
        this.regTime = regTime;
        this.updateTime = updateTime;
    }
}
