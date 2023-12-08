package com.shop.application.port.in.order;

import com.shop.application.dto.OrderHistDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetOrderUseCase {
    Page<OrderHistDto> getOrderList(String email, Pageable pageable);
}
