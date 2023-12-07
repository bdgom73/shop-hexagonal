package com.shop.application.service;

import com.shop.adapter.in.web.dto.input.ItemInput;
import com.shop.application.port.in.order.DoOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService
        implements DoOrderUseCase {
    @Override
    @Transactional
    public void order(String email, ItemInput input) {
    }

}
