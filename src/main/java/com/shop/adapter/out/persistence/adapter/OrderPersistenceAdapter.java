package com.shop.adapter.out.persistence.adapter;

import com.shop.application.port.out.order.CommandOrderPort;
import com.shop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter
        implements CommandOrderPort {

    @Override
    public Order save(Order order) {
       return null;
    }
}
