package com.shop.adapter.in.web.dto;

import com.shop.domain.ItemImage;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ItemImgDto {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto of(ItemImage itemImg) {
        return modelMapper.map(itemImg, ItemImgDto.class);
    }

}