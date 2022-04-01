package me.core.repository;

import me.core.domain.Member;

public interface MemberRepository {

    void save(Member member);

    Member findById(Long id);
}
