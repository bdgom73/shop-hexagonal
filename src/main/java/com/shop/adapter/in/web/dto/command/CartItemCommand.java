package com.shop.adapter.in.web.dto.command;

import com.shop.application.dto.request.CartItemRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemCommand {

    @NotNull(message = "상품 아이디는 필수 입력 값 입니다.")
    private Long itemId;

    @Min(value = 1, message = "최소 1개 이상 담아주세요")
    private int count;

    public CartItemRequest toRequest() {
        return CartItemRequest.builder()
                .itemId(itemId)
                .count(count)
                .build();
    }
}