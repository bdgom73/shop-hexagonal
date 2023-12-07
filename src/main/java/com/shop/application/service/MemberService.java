package com.shop.application.service;

import com.shop.adapter.in.web.dto.input.MemberInput;
import com.shop.application.dto.MemberDto;
import com.shop.application.port.in.SignupMemberUseCase;
import com.shop.application.port.out.CommandMemberPort;
import com.shop.application.port.out.LoadMemberPort;
import com.shop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService
        implements SignupMemberUseCase,
        UserDetailsService {

    private final CommandMemberPort commandMemberPort;
    private final LoadMemberPort loadMemberPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public MemberDto signup(MemberInput input) {
        validateDuplicateMember(input);
        input.setPassword(passwordEncoder.encode(input.getPassword()));
        Member member = commandMemberPort.save(input.toDomain());
        return new MemberDto(member);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> findMember = loadMemberPort.loadByEmail(email);

        if (findMember.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }

        Member member = findMember.get();

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    /**
     * 회원 가입 시 이메일 중복을 검사
     * @param input 회원 정보를 담고 있는 객체
     * @throws IllegalStateException 이미 가입된 회원인 경우 발생 하는 예외
     */
    private void validateDuplicateMember(MemberInput input){
        Optional<Member> member = loadMemberPort.loadByEmail(input.getEmail());

        if(member.isPresent()) {
            throw new IllegalStateException("이미 가입된 회원 입니다.");
        }
    }
}
