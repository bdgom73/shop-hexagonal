package com.shop.application.service;

import com.shop.application.dto.request.ItemRequest;
import com.shop.application.port.in.order.DoOrderUseCase;
import com.shop.application.port.out.item.LoadItemPort;
import com.shop.application.port.out.member.LoadMemberPort;
import com.shop.application.port.out.order.CommandOrderPort;
import com.shop.domain.Item;
import com.shop.domain.Member;
import com.shop.domain.Order;
import com.shop.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService
        implements DoOrderUseCase {

    private final LoadItemPort loadItemPort;
    private final LoadMemberPort loadMemberPort;
    private final CommandOrderPort commandOrderPort;

    @Override
    @Transactional
    public void order(String email, ItemRequest request) {
        Item item = loadItemPort.loadById(request.getItemId())
                .orElseThrow(() -> new IllegalStateException("찾을 수 없는 아이템 입니다."));
        Member member = loadMemberPort.loadByEmail(email)
                .orElseThrow(() -> new IllegalStateException("찾을 수 없는 회원 입니다."));
        OrderItem orderItem = OrderItem.createOrderItem(item, request.getCount());
        Order order = Order.createOrder(member, Collections.singletonList(orderItem));
        commandOrderPort.save(order);
    }



}
