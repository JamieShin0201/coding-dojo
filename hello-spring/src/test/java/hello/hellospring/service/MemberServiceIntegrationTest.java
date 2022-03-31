package hello.hellospring.service;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
public class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;

    @Autowired
    ApplicationContext ac;

    Member memberA;
    Member memberB;

    @BeforeEach
    void setUp() {
        memberA = Member.builder()
                .name("memberA")
                .build();

        memberB = Member.builder()
                .name("memberB")
                .build();
    }

    @Test
    void 회원가입() {
        memberService.join(memberA);

        assertThat(memberA.getId()).isNotNull();
    }

    @Test
    void 중복_회원_예외() {
        memberService.join(memberA);

        assertThrows(IllegalStateException.class, () -> memberService.join(memberA));
    }
}
