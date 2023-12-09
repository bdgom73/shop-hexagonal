package com.shop.application.dto;

import com.shop.adapter.out.constant.ItemSellStatus;
import com.shop.domain.Item;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ItemResponse {

    private Long id;

    private String itemNm;

    private Integer price;

    private String itemDetail;

    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgResponse> itemImgDtoList = new ArrayList<>();

    private List<Long> itemImgIds;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    @Builder
    public ItemResponse(Long id, String itemNm, Integer price, String itemDetail, Integer stockNumber, ItemSellStatus itemSellStatus, List<Long> itemImgIds, LocalDateTime regTime, LocalDateTime updateTime) {
        this.id = id;
        this.itemNm = itemNm;
        this.price = price;
        this.itemDetail = itemDetail;
        this.stockNumber = stockNumber;
        this.itemSellStatus = itemSellStatus;
        this.itemImgIds = ObjectUtils.isEmpty(itemImgIds) ? new ArrayList<>() : itemImgIds;
        this.regTime = regTime;
        this.updateTime = updateTime;
    }

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemResponse of(Item item){
        return modelMapper.map(item, ItemResponse.class);
    }
}