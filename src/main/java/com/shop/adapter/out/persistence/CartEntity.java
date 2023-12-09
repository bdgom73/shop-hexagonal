package com.shop.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cart")
@Getter @Setter
@ToString
public class CartEntity extends BaseEntity {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private MemberEntity member;

    public static CartEntity createCart(MemberEntity member){
        CartEntity cart = new CartEntity();
        cart.setMember(member);
        return cart;
    }

    @Builder
    public CartEntity(Long id, MemberEntity member) {
        this.id = id;
        this.member = member;
    }
}