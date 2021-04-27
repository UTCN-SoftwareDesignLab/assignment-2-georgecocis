package com.lab4.demo.item;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.item.model.Item;
import com.lab4.demo.item.model.dto.ItemDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    public void beforeEach() {
        itemRepository.deleteAll();
    }

    @Test
    public void testMock() {
        Item itemSaved = itemRepository.save(Item.builder().title("whatever").build());

        assertNotNull(itemSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            itemRepository.save(Item.builder().build());
        });
    }

    @Test
    public void testFindAll() {
        List<Item> items = TestCreationFactory.listOf(Item.class);
        itemRepository.saveAll(items);
        List<Item> all = itemRepository.findAll();
        assertEquals(items.size(), all.size());
    }

    @Test
    public void testFindBookFields(){
        String string = "TEST";

        Item item = TestCreationFactory.newItem();
        Item item2 = TestCreationFactory.newItem();
        Item item3 = TestCreationFactory.newItem();

        item.setTitle(string);
        item2.setAuthor(string);
        item3.setGenre(string);

        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item2);
        items.add(item3);

        itemRepository.saveAll(items);

        List<Item> itz = itemRepository.findBookFields(string);

        Assertions.assertEquals(items.size(), itz.size());
    }
}