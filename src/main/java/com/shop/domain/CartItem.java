package com.shop.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
public class CartItem {
    private Long id;
    private Cart cart;
    private Item item;
    private int count;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    @Builder
    public CartItem(Long id, Cart cart, Item item, int count, LocalDateTime regTime, LocalDateTime updateTime) {
        this.id = id;
        this.cart = cart;
        this.item = item;
        this.count = count;
        this.regTime = regTime;
        this.updateTime = updateTime;
    }

    public static CartItem createCartItem(Cart cart, Item item, int count) {
        return CartItem.builder()
                .id(null)
                .cart(cart)
                .item(item)
                .count(count)
                .build();
    }

    public void addCount(int count){
        this.count += count;
    }
}