package com.shop.adapter.in.web.dto;

import com.shop.adapter.out.constant.ItemSellStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ItemFormDto(Long id,
                          @NotBlank(message = "상품명은 필수 입력 값입니다.") String itemNm,
                          @NotNull(message = "가격은 필수 입력 값입니다.") Integer price,
                          @NotBlank(message = "상품 상세는 필수 입력 값입니다.") String itemDetail,
                          @NotNull(message = "재고는 필수 입력 값입니다.") Integer stockNumber,
                          ItemSellStatus itemSellStatus,
                          List<Long> itemImgIds) {


    public static ItemFormDto empty() {
        return new ItemFormDto(null, null, null, null, null, null, null);
    }
}
