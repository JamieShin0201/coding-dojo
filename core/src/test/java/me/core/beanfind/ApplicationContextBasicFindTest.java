package me.core.beanfind;

import me.core.AppConfig;
import me.core.service.MemberService;
import me.core.service.SimpleMemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @DisplayName("이름 없이 타입만으로 조회")
    @Test
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);
        assertThat(memberService).isInstanceOf(SimpleMemberService.class);
    }

    @DisplayName("빈 이름으로 조회")
    @Test
    void findBeanByName1() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);
        assertThat(memberService).isInstanceOf(SimpleMemberService.class);
    }

    @DisplayName("구체 타입으로 조회")
    @Test
    void findBeanByName2() {
        MemberService memberService = ac.getBean("memberService", SimpleMemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);
        assertThat(memberService).isInstanceOf(SimpleMemberService.class);
    }

    @DisplayName("빈 이름으로 조회 - 실패")
    @Test
    void findBeanByNameX() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> {
            ac.getBean("xxxx", MemberService.class);
        });
    }
}
