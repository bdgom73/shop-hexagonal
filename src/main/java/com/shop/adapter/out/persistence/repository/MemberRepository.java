package com.shop.adapter.out.persistence.repository;


import com.shop.adapter.out.persistence.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByEmail(String email);

}