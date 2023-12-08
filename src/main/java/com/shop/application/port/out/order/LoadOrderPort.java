package com.shop.application.port.out.order;

import com.shop.domain.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LoadOrderPort {

    Optional<Order> loadById(Long id);

    List<Order> loadByEmail(String email, Pageable pageable);

    Long loadOrderCountByEmail(String email);
}
