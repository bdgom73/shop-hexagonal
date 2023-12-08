package com.shop.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ItemImage {

    private Long id;

    private String imageName; //이미지 파일명

    private String originalFileName; //원본 이미지 파일명

    private String imageUrl; //이미지 조회 경로

    private boolean isRepresentative; //대표 이미지 여부

    private Item item;

    @Builder
    public ItemImage(Long id, String imageName, String originalFileName, String imageUrl, boolean isRepresentative, Item item) {
        this.id = id;
        this.imageName = imageName;
        this.originalFileName = originalFileName;
        this.imageUrl = imageUrl;
        this.isRepresentative = isRepresentative;
        this.item = item;
    }
}
