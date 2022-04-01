package me.core.service;

import me.core.domain.Member;
import me.core.domain.Order;
import me.core.domain.discount.DiscountPolicy;
import me.core.domain.discount.FixDiscountPolicy;
import me.core.repository.MemberRepository;
import me.core.repository.MemoryMemberRepository;

public class SimpleOrderService implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
