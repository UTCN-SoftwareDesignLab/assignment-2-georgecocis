package com.lab4.demo.item;

import com.lab4.demo.item.model.dto.ItemDTO;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(ITEMS)
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ReportServiceFactory reportServiceFactory;

    @GetMapping
    public List<ItemDTO> allItems() {
        return itemService.findAll();
    }

    @PostMapping
    public ItemDTO create(@RequestBody ItemDTO item) {
        return itemService.create(item);
    }

    @PutMapping(ENTITY)
    public ItemDTO edit(@PathVariable Long id, @RequestBody ItemDTO item) {
        return itemService.edit(id, item);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }

    @GetMapping(ENTITY)
    public ItemDTO getItem(@PathVariable Long id) {
        return itemService.get(id);
    }

    @PatchMapping(ITEMS_EMP)
    public ItemDTO sell(@RequestBody ItemDTO itemDTO){
        return itemService.sell(itemDTO);
    }

    @GetMapping(SEARCH_BOOK)
    public List<ItemDTO> searchBook(@PathVariable String string){
        return itemService.findBooksByFields(string);
    }

    @GetMapping(EXPORT_REPORT)
    public void exportReport(@PathVariable ReportType type) throws IOException {
        List<ItemDTO> IDL = itemService.findOutOfStock();
        reportServiceFactory.getReportService(type).export(IDL);
    }
}
