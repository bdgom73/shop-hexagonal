package com.shop.adapter.in.web;

import com.shop.adapter.in.web.dto.command.MemberCommand;
import com.shop.application.port.in.member.SignupMemberUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final SignupMemberUseCase loadMemberUseCase;

    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberInput", new MemberCommand());
        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    public String newMember(@ModelAttribute("memberInput") @Valid MemberCommand input, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "member/memberForm";
        }

        try {
            loadMemberUseCase.signup(input.toRequest());
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String loginMember(){
        return "/member/memberLoginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }
}
