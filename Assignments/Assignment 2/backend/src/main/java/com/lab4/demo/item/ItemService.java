package com.lab4.demo.item;

import com.lab4.demo.item.model.Item;
import com.lab4.demo.item.model.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    private Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
    }

    public List<ItemDTO> findAll() {
        return itemRepository.findAll().stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ItemDTO> findOutOfStock(){
        return itemRepository.findAll().stream()
                .map(itemMapper::toDto)
                .filter(item -> item.getQuantity() == 0)
                .collect(Collectors.toList());
    }

    public ItemDTO create(ItemDTO item) {
        return itemMapper.toDto(itemRepository.save(
                itemMapper.fromDto(item)
        ));
    }

    public ItemDTO edit(Long id, ItemDTO itemDTO){
        Item book = findById(id);
        book.setTitle(itemDTO.getTitle());
        book.setAuthor(itemDTO.getAuthor());
        book.setGenre(itemDTO.getGenre());
        book.setPrice(itemDTO.getPrice());
        book.setQuantity(itemDTO.getQuantity());
        return itemMapper.toDto(itemRepository.save(book));
    }

    public ItemDTO sell(ItemDTO itemDTO){
        Item book = itemMapper.fromDto(itemDTO);
        if (book.getQuantity() >= 1){
            book.setQuantity(book.getQuantity() - 1);
            return itemMapper.toDto(itemRepository.save(book));
        }
        else throw new EntityNotFoundException("Book " + book.getId() + " out of stock");
    }

    public List<ItemDTO> findBooksByFields(String book){
        return itemRepository.findBookFields(book).stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    public ItemDTO get(Long id) {
        return itemMapper.toDto(findById(id));
    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }
}
