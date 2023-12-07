package com.shop.adapter.out.persistence.adapter;

import com.shop.adapter.out.persistence.MemberEntity;
import com.shop.adapter.out.persistence.mapper.MemberMapper;
import com.shop.adapter.out.persistence.repository.MemberRepository;
import com.shop.application.port.out.CommandMemberPort;
import com.shop.application.port.out.LoadMemberPort;
import com.shop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberPersistenceAdapter
        implements LoadMemberPort,
        CommandMemberPort {

    private final MemberRepository memberRepository;

    @Override
    public Optional<Member> loadById(Long id) {
        return memberRepository.findById(id)
                .map(MemberMapper::mapToDomain);
    }

    @Override
    public Optional<Member> loadByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(MemberMapper::mapToDomain);
    }

    @Override
    public Member save(Member member) {
        MemberEntity memberEntity = memberRepository.save(MemberMapper.mapToEntity(member));
        return MemberMapper.mapToDomain(memberEntity);
    }

    @Override
    public Member update(Long id, Member member) {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 회원 입니다"));
        memberEntity.update(MemberMapper.mapToEntity(member));
        return MemberMapper.mapToDomain(memberEntity);
    }
}
