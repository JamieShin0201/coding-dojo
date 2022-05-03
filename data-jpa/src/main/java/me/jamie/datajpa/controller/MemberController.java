package me.jamie.datajpa.controller;

import lombok.RequiredArgsConstructor;
import me.jamie.datajpa.domain.Member;
import me.jamie.datajpa.dto.MemberDto;
import me.jamie.datajpa.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/members")
    public Page<MemberDto> list(Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(MemberDto::of);
    }

    @GetMapping("/members_page")
    public Page<Member> list2(@PageableDefault(size = 12, sort = "username", direction = Sort.Direction.DESC) Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    @GetMapping("/members_list")
    public Page<Member> list3(
            @Qualifier("member") Pageable memberPageable,
            @Qualifier("order") Pageable orderPageable
    ) {
        return memberRepository.findAll(memberPageable);
    }
}
