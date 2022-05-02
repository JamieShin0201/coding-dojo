package me.jamie.datajpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @GeneratedValue
    @Id
    private Long id;

    private String username;

    public Member(String username) {
        this.username = username;
    }
}
