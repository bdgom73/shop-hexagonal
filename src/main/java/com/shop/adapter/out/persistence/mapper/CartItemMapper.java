package com.shop.adapter.out.persistence.mapper;

import com.shop.adapter.out.persistence.CartItemEntity;
import com.shop.domain.CartItem;
import org.springframework.util.ObjectUtils;

public class CartItemMapper {

    /**
     * 도메인 객체를 엔티티 객체로 변환
     * @param cartItem 장바구니 상품 도메인 객체
     * */
    public static CartItemEntity mapToEntity(CartItem cartItem) {
        return CartItemEntity.builder()
                .id(cartItem.getId())
                .count(cartItem.getCount())
                .item(! ObjectUtils.isEmpty(cartItem.getItem()) ? ItemMapper.mapToEntity(cartItem.getItem()) : null)
                .cart(! ObjectUtils.isEmpty(cartItem.getCart()) ? CartMapper.mapToEntity(cartItem.getCart()) : null)
                .build();
    }

    /**
     * 엔티티 객체를 도메인 객체로 변환
     * @param entity 장바구니 상품 엔티티 객체
     * */
    public static CartItem mapToDomain(CartItemEntity entity) {
        return CartItem.builder()
                .id(entity.getId())
                .count(entity.getCount())
                .updateTime(entity.getUpdateTime())
                .regTime(entity.getRegTime())
                .build();
    }

    /**
     * 연관 관계
     * - 상품 테이블
     * - 장바구니 테이블
     * <br/>
     * 엔티티 객체를 도메인 객체로 변환
     * @param entity 장바구니 엔티티 객체
     * */
    public static CartItem mapToDomainWithAll(CartItemEntity entity) {
        return CartItem.builder()
                .id(entity.getId())
                .count(entity.getCount())
                .item(ItemMapper.mapToDomain(entity.getItem()))
                .cart(CartMapper.mapToDomain(entity.getCart()))
                .updateTime(entity.getUpdateTime())
                .regTime(entity.getRegTime())
                .build();
    }
}
