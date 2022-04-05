package me.core.autowired;

import me.core.AutoAppConfig;
import me.core.domain.Grade;
import me.core.domain.Member;
import me.core.domain.discount.DiscountPolicy;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);

        int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");
        int fixDiscountPrice = discountService.discount(member, 20000, "fixDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(rateDiscountPrice).isEqualTo(2000);
        assertThat(fixDiscountPrice).isEqualTo(1000);
    }

    static class DiscountService {

        private List<DiscountPolicy> discountPolicies;
        private Map<String, DiscountPolicy> discountPolicyMap;

        public DiscountService(List<DiscountPolicy> discountPolicies, Map<String, DiscountPolicy> discountPolicyMap) {
            this.discountPolicies = discountPolicies;
            this.discountPolicyMap = discountPolicyMap;
            System.out.println("discountPolicies = " + discountPolicies);
            System.out.println("discountPolicyMap = " + discountPolicyMap);
        }

        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = discountPolicyMap.get(discountCode);
            System.out.println("discountCode = " + discountCode);
            System.out.println("discountPolicy = " + discountPolicy);

            return discountPolicy.discount(member, price);
        }
    }
}
