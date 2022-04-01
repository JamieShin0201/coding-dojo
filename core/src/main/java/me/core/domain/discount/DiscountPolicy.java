package me.core.domain.discount;

import me.core.domain.Member;

public interface DiscountPolicy {

    int discount(Member member, int price);
}
