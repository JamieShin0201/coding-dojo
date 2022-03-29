package me.jpashop.repository;

import me.jpashop.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testMember() {
        Member member = new Member();
        member.setName("memberA");

        memberRepository.save(member);
        Member foundMember = memberRepository.findOne(member.getId());

        assertThat(foundMember.getId()).isEqualTo(member.getId());
        assertThat(foundMember.getName()).isEqualTo(member.getName());

        // JPA 엔터티 동일성 보장
        assertThat(foundMember).isEqualTo(member);
    }
}
