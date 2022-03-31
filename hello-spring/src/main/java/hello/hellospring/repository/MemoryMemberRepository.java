package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryMemberRepository implements MemberRepository {

    private static final Map<Long, Member> store = new ConcurrentHashMap<>();

    private static final AtomicLong sequence = new AtomicLong();

    @Override
    public Member save(Member member) {
        long seq = sequence.addAndGet(1);
        member.setId(seq);
        store.put(seq, member);
        return member;
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(m -> m.getName().equals(name))
                .findAny();
    }

    public void clearStore() {
        store.clear();
    }
}
