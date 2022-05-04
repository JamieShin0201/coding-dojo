package me.jamie.datajpa.domain;

public interface UsernameOnly {

    // Closed Projections
    String getUsername();

//    // Open Projections
//    @Value("#{target.username + ' ' + target.age + ' ' + target.team.name}")
//    String getUsername();
}
