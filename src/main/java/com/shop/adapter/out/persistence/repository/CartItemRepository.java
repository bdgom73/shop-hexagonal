package com.shop.adapter.out.persistence.repository;


import com.shop.adapter.out.persistence.CartItemEntity;
import com.shop.adapter.out.persistence.repository.dto.CartDetailTuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {

    Optional<CartItemEntity> findByCartIdAndItemId(Long cartId, Long itemId);

    @Query("select new com.shop.adapter.out.persistence.repository.dto.CartDetailTuple(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) " +
            "from CartItemEntity ci, ItemImgEntity im " +
            "join ci.item i " +
            "where ci.cart.id = :cartId " +
            "and im.item.id = ci.item.id " +
            "and im.repimgYn = 'Y' " +
            "order by ci.regTime desc"
            )
    List<CartDetailTuple> findCartDetailDtoList(Long cartId);

}