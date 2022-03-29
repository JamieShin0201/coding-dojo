package me.jpashop.service;

import me.jpashop.entity.item.Book;
import me.jpashop.entity.item.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Test
    void 상품생성() {
        Item book = new Book();
        book.setPrice(1000);
        book.setStockQuantity(10);

        itemService.saveItem(book);
        Item foundItem = itemService.findOne(book.getId());

        assertThat(foundItem).isEqualTo(book);
    }
}
