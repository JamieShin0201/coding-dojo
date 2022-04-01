package me.core.service;

import me.core.domain.Member;
import me.core.repository.MemberRepository;
import me.core.repository.MemoryMemberRepository;

public class SimpleMemberService implements MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long id) {
        return memberRepository.findById(id);
    }
}
