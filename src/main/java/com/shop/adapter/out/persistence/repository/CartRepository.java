package com.shop.adapter.out.persistence.repository;


import com.shop.adapter.out.persistence.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    Optional<CartEntity> findByMemberId(Long memberId);

}