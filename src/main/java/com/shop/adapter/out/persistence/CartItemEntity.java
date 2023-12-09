package com.shop.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name="cart_item")
@NoArgsConstructor
public class CartItemEntity extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cart_id")
    private CartEntity cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    private int count;

    @Builder
    public CartItemEntity(Long id, CartEntity cart, ItemEntity item, int count) {
        this.id = id;
        this.cart = cart;
        this.item = item;
        this.count = count;
    }

    public static CartItemEntity createCartItem(CartEntity cart, ItemEntity item, int count) {
        CartItemEntity cartItem = new CartItemEntity();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setCount(count);
        return cartItem;
    }

    public void addCount(int count){
        this.count += count;
    }

    public void updateCount(int count){
        this.count = count;
    }

}