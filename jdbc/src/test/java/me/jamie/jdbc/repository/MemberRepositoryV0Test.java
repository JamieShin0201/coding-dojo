package me.jamie.jdbc.repository;

import me.jamie.jdbc.domain.Member;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        Member member = new Member("memberV0", 10000);
        repository.save(member);

        Member foundMember = repository.findById(member.getMemberId());
        assertThat(foundMember).isEqualTo(member);
    }
}
