package com.shop.application.dto;

import com.shop.domain.ItemImage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class ItemImgResponse {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    @Builder
    public ItemImgResponse(Long id, String imgName, String oriImgName, String imgUrl, String repImgYn) {
        this.id = id;
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
        this.repImgYn = repImgYn;
    }

    public static ItemImgResponse of(ItemImage itemImg) {
        return ItemImgResponse.builder()
                .id(itemImg.getId())
                .imgName(itemImg.getImageName())
                .oriImgName(itemImg.getOriginalFileName())
                .imgUrl(itemImg.getImageUrl())
                .repImgYn(itemImg.isRepresentative() ? "Y" : "N")
                .build();
    }

}