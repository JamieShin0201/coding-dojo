package me.jamie.jdbc.service;

import lombok.extern.slf4j.Slf4j;
import me.jamie.jdbc.domain.Member;
import me.jamie.jdbc.repository.MemberRepositoryV3;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
class MemberServiceV3_4Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    @Autowired
    private MemberServiceV3_3 memberService;

    @Autowired
    private MemberRepositoryV3 memberRepository;

    @TestConfiguration
    static class TestConfig {

        private final DataSource dataSource;

        public TestConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Bean
        PlatformTransactionManager transactionManager() {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        MemberRepositoryV3 memberRepository() {
            return new MemberRepositoryV3(dataSource);
        }

        @Bean
        MemberServiceV3_3 memberService() {
            return new MemberServiceV3_3(memberRepository());
        }
    }

    @AfterEach
    void after() throws SQLException {
        memberRepository.delete(MEMBER_A);
        memberRepository.delete(MEMBER_B);
        memberRepository.delete(MEMBER_EX);
    }

    @Test
    void aopCheck() {
        log.info("memberService class={}", memberService.getClass());
        log.info("memberRepository class={}", memberRepository.getClass());
        assertThat(AopUtils.isAopProxy(memberService)).isTrue();
        assertThat(AopUtils.isAopProxy(memberRepository)).isFalse();
    }

    @DisplayName("정상 이체")
    @Test
    void accountTransfer() throws SQLException {
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_B, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        memberService.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);

        Member foundMemberA = memberRepository.findById(memberA.getMemberId());
        Member foundMemberB = memberRepository.findById(memberB.getMemberId());
        assertThat(foundMemberA.getMoney()).isEqualTo(8000);
        assertThat(foundMemberB.getMoney()).isEqualTo(12000);
    }

    @DisplayName("이체 중 예외 발생")
    @Test
    void accountTransferEx() throws SQLException {
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberEx = new Member(MEMBER_EX, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberEx);

        assertThatThrownBy(() -> memberService.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 2000))
                .isInstanceOf(IllegalStateException.class);

        Member foundMemberA = memberRepository.findById(memberA.getMemberId());
        Member foundMemberEx = memberRepository.findById(memberEx.getMemberId());
        assertThat(foundMemberA.getMoney()).isEqualTo(10000);
        assertThat(foundMemberEx.getMoney()).isEqualTo(10000);
    }
}
