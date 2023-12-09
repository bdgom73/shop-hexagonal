package com.shop.adapter.out.persistence.mapper;

import com.shop.adapter.out.persistence.CartEntity;
import com.shop.domain.Cart;

public class CartMapper {

    /**
     * 도메인 객체를 엔티티 객체로 변환
     * @param cart 장바구니 도메인 객체
     * */
    public static CartEntity mapToEntity(Cart cart) {
        return new CartEntity(cart.getId(), MemberMapper.mapToEntity(cart.getMember()));
    }

    /**
     * 엔티티 객체를 도메인 객체로 변환
     * @param entity 장바구니 엔티티 객체
     * */
    public static Cart mapToDomain(CartEntity entity) {
        return Cart.builder()
                .id(entity.getId())
                .updateTime(entity.getUpdateTime())
                .regTime(entity.getRegTime())
                .build();
    }

    /**
     * 연관 관계
     * - 회원 테이블
     * <br/>
     * 엔티티 객체를 도메인 객체로 변환
     * @param entity 장바구니 엔티티 객체
     * */
    public static Cart mapToDomainWithMember(CartEntity entity) {
        return Cart.builder()
                .id(entity.getId())
                .member(MemberMapper.mapToDomain(entity.getMember()))
                .updateTime(entity.getUpdateTime())
                .regTime(entity.getRegTime())
                .build();
    }
}
