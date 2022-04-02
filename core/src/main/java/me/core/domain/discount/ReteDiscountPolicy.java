package me.core.domain.discount;

import me.core.domain.Grade;
import me.core.domain.Member;

public class ReteDiscountPolicy implements DiscountPolicy{

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP) {
            return (price * discountPercent) / 100;
        } else {
            return 0;
        }
    }
}
