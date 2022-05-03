package me.jamie.datajpa.repository;

import me.jamie.datajpa.domain.Member;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Member> findMemberCustom();
}
