package com.shop.adapter.out.persistence.mapper;

import com.shop.adapter.out.persistence.MemberEntity;
import com.shop.domain.Member;

public class MemberMapper {

    public static MemberEntity mapToEntity(Member member) {
        return MemberEntity.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .password(member.getPassword())
                .role(member.getRole())
                .address(member.getAddress())
                .build();
    }

    public static Member mapToDomain(MemberEntity entity) {
        return new Member(entity);
    }

}
