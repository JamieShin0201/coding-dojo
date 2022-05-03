package me.jamie.datajpa.repository;

import me.jamie.datajpa.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
