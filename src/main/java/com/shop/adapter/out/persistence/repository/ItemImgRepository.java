package com.shop.adapter.out.persistence.repository;


import com.shop.adapter.out.persistence.ItemImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImgEntity, Long> {

    List<ItemImgEntity> findByItemIdOrderByIdAsc(Long itemId);

    ItemImgEntity findByItemIdAndRepimgYn(Long itemId, String repimgYn);

}