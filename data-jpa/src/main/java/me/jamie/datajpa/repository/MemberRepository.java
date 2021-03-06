package me.jamie.datajpa.repository;

import me.jamie.datajpa.domain.*;
import me.jamie.datajpa.dto.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom, JpaSpecificationExecutor<Member> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select new me.jamie.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username = :name")
    List<Member> findMembers(@Param("name") String username);

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    Page<Member> findByAge(int age, Pageable pageable);

    @Modifying
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    // 공통 메서드 오버라이드
    @EntityGraph(attributePaths = {"team"})
    @Override
    List<Member> findAll();

    // JPQL + 엔터티 그래프
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    @EntityGraph(attributePaths = {"team"})
    List<Member> findByAge(int age);

    @EntityGraph("Member.all")
    @Query("select m from Member m")
    List<Member> findMemberNamedEntityGraph();

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"), forCounting = true)
    Page<Member> findByUsername(String name, Pageable pageable);

    List<UsernameOnly> findProjectionsByUsername(String username);

    List<UsernameOnlyDto> findClassProjectionsByUsername(String username);

    <T> List<T> findGenericProjectionsByUsername(String username, Class<T> type);

    List<NestedClosedProjection> findNestedProjectionsByUsername(String username);

    @Query(value = "select * from member where username = ?", nativeQuery = true)
    Member findByNativeQuery(String username);

    @Query(value = "SELECT m.id, m.username, t.name as teamName FROM member m left join team t",
            countQuery = "SELECT count(*) from member",
            nativeQuery = true)
    Page<MemberProjection> findNativeProjection(Pageable pageable);
}
