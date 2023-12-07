package com.shop.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class MainController {
    @GetMapping(value = "/")
    public String main(Model model){
        model.addAttribute("data", "타임리프 예제 입니다.");
        return "thymeleafEx/thymeleafEx01";
    }

}