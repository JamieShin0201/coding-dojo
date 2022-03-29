package me.jpashop.member;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Setter
@Getter
@Entity
public class Member {

    @GeneratedValue
    @Id
    private Long id;

    private String username;
}
