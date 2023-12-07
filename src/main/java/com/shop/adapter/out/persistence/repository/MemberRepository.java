package com.shop.adapter.out.persistence.repository;


import com.shop.adapter.out.persistence.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    MemberEntity findByEmail(String email);

}