package com.shop.application.port.out.order;

import com.shop.domain.Order;

public interface CommandOrderPort {
    Order save(Order order);
}
