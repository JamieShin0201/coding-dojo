package me.jamie.datajpa.domain;

public interface NestedClosedProjection {

    String getUsername();

    TeamInfo getTeam();

    interface TeamInfo {
        String getTeam();
    }
}
