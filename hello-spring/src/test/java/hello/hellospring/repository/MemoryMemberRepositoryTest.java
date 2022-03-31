package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository memberRepository;

    Member memberA;
    Member memberB;

    @BeforeEach
    void setUp() {
        memberRepository = new MemoryMemberRepository();

        memberA = Member.builder()
                .name("memberA")
                .build();

        memberB = Member.builder()
                .name("memberB")
                .build();
    }

    @AfterEach
    void clear() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        Member savedMember = memberRepository.save(memberA);

        assertThat(savedMember.getId()).isNotNull();
    }

    @Test
    void findByName() {
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        Member foundMember = memberRepository.findByName(memberA.getName()).get();
        assertThat(foundMember.getName()).isEqualTo(memberA.getName());
    }

    @Test
    void findAll() {
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        List<Member> members = memberRepository.findAll();
        assertThat(members).contains(memberA, memberB);
    }
}
