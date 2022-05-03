package me.jamie.datajpa.repository;

import me.jamie.datajpa.domain.Member;
import me.jamie.datajpa.domain.Team;
import me.jamie.datajpa.dto.MemberDto;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnitUtil;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    void testMember() {
        Member member = new Member("memberA");

        Member savedMember = memberRepository.save(member);
        Member foundMember = memberRepository.findById(savedMember.getId()).get();

        assertThat(foundMember.getId()).isEqualTo(savedMember.getId());
        assertThat(foundMember.getUsername()).isEqualTo(savedMember.getUsername());
        assertThat(foundMember).isSameAs(savedMember);
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member foundMember1 = memberRepository.findById(member1.getId()).get();
        Member foundMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(foundMember1).isSameAs(member1);
        assertThat(foundMember2).isSameAs(member2);

        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(2);

        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long countAfterDelete = memberRepository.count();
        assertThat(countAfterDelete).isEqualTo(0);
    }

    @Test
    void findByUsernameAndAgeGreaterThan() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> users = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getUsername()).isEqualTo("AAA");
        assertThat(users.get(0).getAge()).isEqualTo(20);
    }

    @Test
    void findByUsername() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> users = memberRepository.findByUsername("AAA");
        assertThat(users).hasSize(2);
    }

    @Test
    void findUser() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> users = memberRepository.findUser("AAA", 10);
        assertThat(users).hasSize(1);
    }

    @Test
    void findMemberDto() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member m1 = new Member("AAA", 10, teamA);
        Member m2 = new Member("AAA", 20, teamB);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<MemberDto> users = memberRepository.findMemberDto();
        assertThat(users).hasSize(2);
        assertThat(users.get(0).getUsername()).isEqualTo("AAA");
        assertThat(users.get(0).getTeamName()).isEqualTo("teamA");
        assertThat(users.get(1).getUsername()).isEqualTo("AAA");
        assertThat(users.get(1).getTeamName()).isEqualTo("teamB");
    }

    @Test
    void findMembers() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> users = memberRepository.findMembers("AAA");
        assertThat(users).hasSize(2);
    }

    @Test
    void findByNames() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        Member m3 = new Member("CCC", 30);
        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(m3);

        List<Member> users = memberRepository.findByNames(List.of("AAA", "CCC"));
        assertThat(users).hasSize(2);
    }

    @Test
    void paging() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        Page<Member> page = memberRepository.findByAge(age, pageRequest);
        List<Member> members = page.getContent();

        assertThat(members).hasSize(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
    }

    @Test
    void bulkUpdate() {
        memberRepository.save(new Member("member5", 10));
        memberRepository.save(new Member("member1", 19));
        memberRepository.save(new Member("member2", 20));
        memberRepository.save(new Member("member3", 21));
        memberRepository.save(new Member("member4", 40));

        int resultCount = memberRepository.bulkAgePlus(20);
        assertThat(resultCount).isEqualTo(3);
    }

    @Test
    void findMemberLazy() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        memberRepository.save(new Member("member1", 10, teamA));
        memberRepository.save(new Member("member2", 20, teamB));
        em.flush();
        em.clear();

        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            System.out.println(Hibernate.isInitialized(member.getTeam()));
            System.out.println(em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(member.getTeam()));
            member.getTeam().getName();
        }
    }

    @Test
    void findMemberFetchJoin() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        memberRepository.save(new Member("member1", 10, teamA));
        memberRepository.save(new Member("member2", 20, teamB));
        em.flush();
        em.clear();

        List<Member> members = memberRepository.findMemberFetchJoin();
        for (Member member : members) {
            System.out.println(em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(member.getTeam()));
            member.getTeam().getName();
        }
    }

    @Test
    void findMemberEntityGraph() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        memberRepository.save(new Member("member1", 10, teamA));
        memberRepository.save(new Member("member2", 20, teamB));
        em.flush();
        em.clear();

        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            System.out.println(em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(member.getTeam()));
            member.getTeam().getName();
        }
    }

    @Test
    void findMemberJPQLAndEntityGraph() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        memberRepository.save(new Member("member1", 10, teamA));
        memberRepository.save(new Member("member2", 20, teamB));
        em.flush();
        em.clear();

        List<Member> members = memberRepository.findMemberEntityGraph();
        for (Member member : members) {
            System.out.println(em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(member.getTeam()));
            member.getTeam().getName();
        }
    }

    @Test
    void findByAge() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        memberRepository.save(new Member("member1", 10, teamA));
        memberRepository.save(new Member("member2", 20, teamB));
        em.flush();
        em.clear();

        List<Member> members = memberRepository.findByAge(10);
        for (Member member : members) {
            System.out.println(em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(member.getTeam()));
            member.getTeam().getName();
        }
    }

    @Test
    void findMemberNamedEntityGraph() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        memberRepository.save(new Member("member1", 10, teamA));
        memberRepository.save(new Member("member2", 20, teamB));
        em.flush();
        em.clear();

        PersistenceUnitUtil util = em.getEntityManagerFactory().getPersistenceUnitUtil();
        List<Member> members = memberRepository.findMemberNamedEntityGraph();
        for (Member member : members) {
            assertThat(util.isLoaded(member.getTeam())).isTrue();
            member.getTeam().getName();
        }
    }

    @Test
    void queryHint() {
        memberRepository.save(new Member("member1", 10));
        em.flush();
        em.clear();

        Member member = memberRepository.findReadOnlyByUsername("member1");
        member.setUsername("member2");

        // update query 실행 X
        em.flush();
    }

    @Test
    void findMemberCustom() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 20));
        memberRepository.save(new Member("member3", 30));
        List<Member> members = memberRepository.findMemberCustom();
        assertThat(members).hasSize(3);
    }

    @Test
    void JpaEventBaseEntity() {
        Member member = new Member("member1");
        memberRepository.save(member);

        member.setUsername("member2");

        em.flush();
        em.clear();

        Member foundMember = memberRepository.findById(member.getId()).get();

        assertThat(foundMember.getCreatedDate()).isNotNull();
        assertThat(foundMember.getLastModifiedDate()).isNotNull();
    }
}
