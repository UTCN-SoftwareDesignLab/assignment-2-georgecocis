package com.lab4.demo.item;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.item.model.Item;
import com.lab4.demo.item.model.dto.ItemDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ItemServiceIntegrationTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Item> items = TestCreationFactory.listOf(Item.class);
        itemRepository.saveAll(items);

        List<ItemDTO> all = itemService.findAll();

        Assertions.assertEquals(items.size(), all.size());
    }

    @Test
    void create(){
        ItemDTO item = TestCreationFactory.newItemDTO();
        ItemDTO itemDTO = itemService.create(item);

        Assertions.assertEquals(itemService.findAll().size(), 1);
        Assertions.assertNotNull(itemDTO);
    }

    @Test
    void delete(){
        ItemDTO item = TestCreationFactory.newItemDTO();
        ItemDTO itemDTO = itemService.create(item);

        Assertions.assertEquals(itemService.findAll().size(), 1);
        itemService.delete(itemDTO.getId());
        Assertions.assertEquals(itemService.findAll().size(), 0);
    }

    @Test
    void sell(){
        ItemDTO item = TestCreationFactory.newItemDTO();
        item.setQuantity(20);

        ItemDTO itemDTO = itemService.create(item);
        Assertions.assertEquals(itemDTO.getQuantity(), 20);

        itemDTO = itemService.sell(itemDTO);
        Assertions.assertEquals(itemDTO.getQuantity() + 1, 20);
    }

    @Test
    void edit(){
        ItemDTO item = TestCreationFactory.newItemDTO();
        ItemDTO item2 = TestCreationFactory.newItemDTO();

        ItemDTO itemDTO = itemService.create(item);
        ItemDTO itemDTO1 = itemService.create(item2);

        Assertions.assertNotEquals(itemDTO, itemDTO1);

        itemDTO = itemService.edit(itemDTO.getId(), itemDTO1);
        itemDTO.setId(itemDTO1.getId());

        Assertions.assertEquals(itemDTO, itemDTO1);
    }

    @Test
    void findOutOfStock(){
        List<Item> items = TestCreationFactory.listOf(Item.class);
        for (Item item: items)
            item.setQuantity(0);
        itemRepository.saveAll(items);

        List<ItemDTO> all = itemService.findOutOfStock();

        Assertions.assertEquals(items.size(), all.size());
    }

    @Test
    void findBooksByFields(){
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

        List<ItemDTO> itz = itemService.findBooksByFields(string);

        Assertions.assertEquals(items.size(), itz.size());
    }
}