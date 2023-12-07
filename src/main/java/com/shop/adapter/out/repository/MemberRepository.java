package com.shop.adapter.out.repository;


import com.shop.adapter.out.persistence.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);

}