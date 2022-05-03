package me.jamie.datajpa.repository;

import me.jamie.datajpa.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
