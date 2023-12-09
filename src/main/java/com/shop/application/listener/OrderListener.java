package com.shop.application.listener;

import com.shop.application.dto.request.ItemRequest;
import com.shop.application.event.OrderEvent;
import com.shop.application.port.in.order.DoOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderListener {

    private final Logger log = LoggerFactory.getLogger(OrderListener.class);

    private final DoOrderUseCase doOrderUseCase;
    @EventListener
    public void handle(OrderEvent event) {
        log.info("주문 이벤트 발생");

        List<ItemRequest> list = event.orderDtoList().stream()
                .map(_this -> new ItemRequest(_this.getItemId(), _this.getCount()))
                .toList();
        doOrderUseCase.orders(event.email(), list);
    }
}
