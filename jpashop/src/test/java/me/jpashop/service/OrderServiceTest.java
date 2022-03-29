package me.jpashop.service;

import me.jpashop.domain.*;
import me.jpashop.domain.item.Book;
import me.jpashop.domain.item.Item;
import me.jpashop.exception.NotEnoughStockException;
import me.jpashop.repository.ItemRepository;
import me.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class OrderServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void 상품주문() {
        Member member = createMember();
        Item item = createBook("JPA", 10000, 10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        Order foundOrder = orderRepository.findOne(orderId);

        assertThat(foundOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(foundOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(foundOrder.getTotalPrice()).isEqualTo(item.getPrice() * orderCount);

        assertThat(item.getStockQuantity()).isEqualTo(8);
    }

    @Test
    void 상품주문_재고수량초과() {
        Member member = createMember();
        Item item = createBook("JPA", 10000, 10);
        int orderCount = item.getStockQuantity() + 1;

        assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), item.getId(), orderCount));
    }

    @Test
    void 주문취소() {
        Member member = createMember();
        Item item = createBook("JPA", 10000, 10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        orderService.cancelOrder(orderId);

        Order foundOrder = orderRepository.findOne(orderId);

        assertThat(foundOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);

        assertThat(item.getStockQuantity()).isEqualTo(10);
    }

    @Test
    void 주문목록조회() {
        Member member = createMember();
        Item item = createBook("JPA", 10000, 10);
        int orderCount = 2;
        orderService.order(member.getId(), item.getId(), orderCount);

        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setMemberName("회원1");
        orderSearch.setOrderStatus(OrderStatus.ORDER);

        List<Order> orders = orderService.findOrders(orderSearch);
        assertThat(orders).isNotEmpty();
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }
}
