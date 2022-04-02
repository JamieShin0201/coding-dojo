package me.core;

import me.core.domain.discount.DiscountPolicy;
import me.core.domain.discount.ReteDiscountPolicy;
import me.core.repository.MemberRepository;
import me.core.repository.MemoryMemberRepository;
import me.core.service.MemberService;
import me.core.service.OrderService;
import me.core.service.SimpleMemberService;
import me.core.service.SimpleOrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        return new SimpleMemberService(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new SimpleOrderService(memberRepository(), discountPolicy());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new ReteDiscountPolicy();
    }
}
