package me.core.repository;

import me.core.domain.Member;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryMemberRepository implements MemberRepository {

    private static final Map<Long, Member> store = new ConcurrentHashMap<>();

    private static final AtomicLong sequence = new AtomicLong();

    @Override
    public void save(Member member) {
        Long newId = sequence.addAndGet(1);
        member.setId(newId);
        store.put(newId, member);
    }

    @Override
    public Member findById(Long id) {
        return store.get(id);
    }
}
