package me.core.service;

import me.core.domain.Member;
import me.core.domain.Order;
import me.core.domain.discount.DiscountPolicy;
import me.core.domain.discount.FixDiscountPolicy;
import me.core.domain.discount.ReteDiscountPolicy;
import me.core.repository.MemberRepository;
import me.core.repository.MemoryMemberRepository;

public class SimpleOrderService implements OrderService {

    private final MemberRepository memberRepository;

    private final DiscountPolicy discountPolicy;

    public SimpleOrderService(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
