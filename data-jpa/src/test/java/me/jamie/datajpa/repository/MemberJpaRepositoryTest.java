package me.jamie.datajpa.repository;

import me.jamie.datajpa.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberRepository;

    @Test
    void testMember() {
        Member member = new Member("memberA");

        Member savedMember = memberRepository.save(member);
        Member foundMember = memberRepository.find(savedMember.getId());

        assertThat(foundMember.getId()).isEqualTo(savedMember.getId());
        assertThat(foundMember.getUsername()).isEqualTo(savedMember.getUsername());
        assertThat(foundMember).isSameAs(savedMember);
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member foundMember1 = memberRepository.findById(member1.getId()).get();
        Member foundMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(foundMember1).isSameAs(member1);
        assertThat(foundMember2).isSameAs(member2);

        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(2);

        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long countAfterDelete = memberRepository.count();
        assertThat(countAfterDelete).isEqualTo(0);
    }

    @Test
    void findByUsernameAndAgeGreaterThan() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> users = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getUsername()).isEqualTo("AAA");
        assertThat(users.get(0).getAge()).isEqualTo(20);
    }

    @Test
    void findByUsername() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> users = memberRepository.findByUsername("AAA");
        assertThat(users).hasSize(2);
    }
}
