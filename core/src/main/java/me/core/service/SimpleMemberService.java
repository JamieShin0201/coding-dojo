package me.core.service;

import me.core.domain.Member;
import me.core.repository.MemberRepository;

public class SimpleMemberService implements MemberService {

    private final MemberRepository memberRepository;

    public SimpleMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long id) {
        return memberRepository.findById(id);
    }
}
