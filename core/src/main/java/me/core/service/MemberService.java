package me.core.service;

import me.core.domain.Member;

public interface MemberService {

    void join(Member member);

    Member findMember(Long id);
}
