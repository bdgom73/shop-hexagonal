package com.shop.adapter.out.persistence.mapper;

import com.shop.adapter.out.persistence.MemberEntity;
import com.shop.domain.Member;

public class MemberMapper {

    /**
     * 도메인 객체를 엔티티 객체로 변환
     * @param member 회원 도메인 객체
     * */
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

    /**
     * 엔티티 객체를 도메인 객체로 변환
     * @param entity 회원 엔티티 객체
     * */
    public static Member mapToDomain(MemberEntity entity) {
        return new Member(entity);
    }

}
