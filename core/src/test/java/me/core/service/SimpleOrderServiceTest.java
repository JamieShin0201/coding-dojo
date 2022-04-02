package me.core.service;

import me.core.AppConfig;
import me.core.domain.Grade;
import me.core.domain.Member;
import me.core.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleOrderServiceTest {

    OrderService orderService;

    MemberService memberService;

    @BeforeEach
    void setUp() {
        AppConfig appConfig = new AppConfig();
        orderService = appConfig.orderService();
        memberService = appConfig.memberService();
    }

    @Test
    void createOrder() {
        Member member = new Member("memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(member.getId(), "itemA", 10000);
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
