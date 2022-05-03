package me.jamie.datajpa.controller;

import lombok.RequiredArgsConstructor;
import me.jamie.datajpa.domain.Member;
import me.jamie.datajpa.repository.MemberRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberRepository memberRepository;

//    @GetMapping("/members/{id}")
//    public String findMember(@PathVariable("id") Long id) {
//        Member member = memberRepository.findById(id).get();
//        return member.getUsername();
//    }

    // 도메인 클래스 컨버터 사용 후
    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Member member) {
        return member.getUsername();
    }
}
