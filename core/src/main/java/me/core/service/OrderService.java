package me.core.service;

import me.core.domain.Order;

public interface OrderService {

    Order createOrder(Long memberId, String itemName, int itemPrice);
}
