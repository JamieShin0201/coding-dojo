package me.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import me.jpashop.domain.item.Item;

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

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int price, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(price);
        orderItem.setCount(count);

        item.removeStock(count);

        return orderItem;
    }

    //==비지니스 로직==//
    public void cancel() {
        item.addStock(count);
    }

    //==조회 로직==//
    public int getTotalPrice() {
        return orderPrice * count;
    }
}
