package me.core;

import me.core.domain.discount.DiscountPolicy;
import me.core.domain.discount.ReteDiscountPolicy;
import me.core.repository.MemberRepository;
import me.core.repository.MemoryMemberRepository;
import me.core.service.MemberService;
import me.core.service.OrderService;
import me.core.service.SimpleMemberService;
import me.core.service.SimpleOrderService;

public class AppConfig {

    public MemberService memberService() {
        return new SimpleMemberService(memberRepository());
    }

    public OrderService orderService() {
        return new SimpleOrderService(memberRepository(), discountPolicy());
    }

    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public DiscountPolicy discountPolicy() {
        return new ReteDiscountPolicy();
    }
}
