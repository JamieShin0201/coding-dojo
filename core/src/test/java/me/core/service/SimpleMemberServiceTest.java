package me.core.service;

import me.core.AppConfig;
import me.core.domain.Grade;
import me.core.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleMemberServiceTest {

    MemberService memberService;

    @BeforeEach
    void setUp() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        memberService = ac.getBean("memberService", MemberService.class);
    }

    @Test
    void join() {
        Member member = new Member("memberA", Grade.VIP);

        memberService.join(member);
        Member foundMember = memberService.findMember(member.getId());

        assertThat(foundMember).isEqualTo(member);
    }
}
