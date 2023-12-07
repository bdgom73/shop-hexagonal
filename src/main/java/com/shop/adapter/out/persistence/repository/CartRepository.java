package com.shop.adapter.out.persistence.repository;


import com.shop.adapter.out.persistence.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    CartEntity findByMemberId(Long memberId);

}