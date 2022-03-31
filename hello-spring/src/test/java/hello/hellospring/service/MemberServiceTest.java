package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;

    MemoryMemberRepository memberRepository;

    Member memberA;
    Member memberB;

    @BeforeEach
    void setUp() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);

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
    void 회원가입() {
        Long joinedMemberId = memberService.join(memberA);

        assertThat(joinedMemberId).isEqualTo(memberA.getId());
    }

    @Test
    void 중복_회원_예외() {
        memberService.join(memberA);

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(memberA));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원 이름 입니다.");
    }
}
