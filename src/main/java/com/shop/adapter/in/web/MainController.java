package com.shop.adapter.in.web;

import com.shop.adapter.in.web.dto.command.ItemCommand;
import com.shop.application.dto.request.ItemRequest;
import com.shop.application.port.in.order.DoOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class MainController {

    private final DoOrderUseCase doOrderUseCase;

    @GetMapping(value = "/")
    public String main(Model model){
        model.addAttribute("data", "타임리프 예제 입니다.");
        return "thymeleafEx/thymeleafEx01";
    }


    @GetMapping("/test/order")
    @ResponseBody
    public void order(
            @RequestBody ItemCommand itemCommand
            ) {
        doOrderUseCase.order("bdgom73@naver.com", itemCommand.toRequest());
    }

    @GetMapping("/test/order2")
    @ResponseBody
    public void order(
            @RequestBody List<ItemCommand> itemCommand
    ) {
        List<ItemRequest> list = itemCommand.stream()
                .map(ItemCommand::toRequest).toList();
        doOrderUseCase.orders("bdgom73@naver.com", list);
    }
}