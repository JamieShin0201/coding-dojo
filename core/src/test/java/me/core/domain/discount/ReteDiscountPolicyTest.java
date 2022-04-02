package me.core.domain.discount;

import me.core.domain.Grade;
import me.core.domain.Member;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ReteDiscountPolicyTest {

    DiscountPolicy discountPolicy = new ReteDiscountPolicy();

    @Test
    void discount_if_member_is_vip() {
        Member member = new Member("memberVIP", Grade.VIP);

        int discountPrice = discountPolicy.discount(member, 20000);

        assertThat(discountPrice).isEqualTo(2000);
    }

    @Test
    void discount_if_member_is_not_vip() {
        Member member = new Member("memberVIP", Grade.BASIC);

        int discountPrice = discountPolicy.discount(member, 20000);

        assertThat(discountPrice).isEqualTo(0);
    }
}
