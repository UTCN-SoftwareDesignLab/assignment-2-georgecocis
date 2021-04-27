package com.lab4.demo.item;

import com.lab4.demo.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT book from Item book where book.genre LIKE ?1 OR book.title LIKE ?1 OR book.author LIKE ?1")
    List<Item> findBookFields(String book);
}
