package com.shop.adapter.out.persistence.repository;


import com.shop.adapter.out.persistence.CartItemEntity;
import com.shop.adapter.out.persistence.repository.dto.CartDetailDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {

    CartItemEntity findByCartIdAndItemId(Long cartId, Long itemId);

    @Query("select new com.shop.adapter.out.persistence.repository.dto.CartDetailDto(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) " +
            "from CartItemEntity ci, ItemImgEntity im " +
            "join ci.item i " +
            "where ci.cart.id = :cartId " +
            "and im.item.id = ci.item.id " +
            "and im.repimgYn = 'Y' " +
            "order by ci.regTime desc"
            )
    List<CartDetailDto> findCartDetailDtoList(Long cartId);

}