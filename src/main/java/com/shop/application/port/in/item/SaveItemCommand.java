package com.shop.application.port.in.item;

import com.shop.adapter.out.constant.ItemSellStatus;
import com.shop.domain.Item;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record SaveItemCommand(String itemName,
                              Integer price,
                              String itemDetail,
                              Integer stockNumber,
                              ItemSellStatus itemSellStatus,
                              List<MultipartFile> files) {

    public Item toDomain() {
        return Item.builder()
                .itemNm(itemName)
                .price(price)
                .itemDetail(itemDetail)
                .itemSellStatus(itemSellStatus)
                .stockNumber(stockNumber)
                .build();
    }
}
