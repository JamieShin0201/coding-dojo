package me.core.service;

import me.core.domain.Grade;
import me.core.domain.Member;
import me.core.domain.Order;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleOrderServiceTest {

    OrderService orderService = new SimpleOrderService();

    MemberService memberService = new SimpleMemberService();

    @Test
    void createOrder() {
        Member member = new Member("memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(member.getId(), "itemA", 10000);
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
