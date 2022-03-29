package me.jpashop.service;

import me.jpashop.domain.Member;
import me.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 회원가입() {
        Member member = new Member();
        member.setName("jamie");

        Long savedId = memberService.join(member);
        Member foundMember = memberService.findOne(savedId);

        assertThat(foundMember).isEqualTo(member);
    }

    @Test
    void 중복_회원_예외() {
        Member member1 = new Member();
        member1.setName("jamie");

        Member member2 = new Member();
        member2.setName("jamie");

        memberService.join(member1);
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }
}
