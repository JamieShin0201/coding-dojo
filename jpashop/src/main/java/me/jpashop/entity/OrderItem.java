package me.jpashop.entity;

import lombok.Getter;
import lombok.Setter;
import me.jpashop.entity.item.Item;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class OrderItem {

    @Column(name = "order_item_id")
    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;
}
