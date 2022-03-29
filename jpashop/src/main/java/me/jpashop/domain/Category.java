package me.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import me.jpashop.domain.item.Item;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Category {

    @Column(name = "category_id")
    @GeneratedValue
    @Id
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    //==연관관계 메서드==//
    public void addChildCategory(Category child) {
        children.add(child);
        child.setParent(this);
    }
}
