package me.jpashop.entity.item;

import lombok.Getter;
import lombok.Setter;
import me.jpashop.entity.Category;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
public abstract class Item {

    @Column(name = "item_id")
    @GeneratedValue
    @Id
    private Long id;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
