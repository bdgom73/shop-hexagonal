package com.shop.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
public class Cart {
    private Long id;
    private Member member;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    @Builder
    public Cart(Long id, Member member, LocalDateTime regTime, LocalDateTime updateTime) {
        this.id = id;
        this.member = member;
        this.regTime = regTime;
        this.updateTime = updateTime;
    }
}