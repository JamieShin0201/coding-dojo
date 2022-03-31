package hello.hellospring.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {

    private Long id;

    private String name;

    public void setId(Long id) {
        this.id = id;
    }

    @Builder
    private Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
