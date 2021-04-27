package com.lab4.demo.item;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.item.model.Item;
import com.lab4.demo.item.model.dto.ItemDTO;
import com.lab4.demo.report.CSVReportService;
import com.lab4.demo.report.PdfReportService;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.lab4.demo.TestCreationFactory.*;
import static com.lab4.demo.UrlMapping.*;
import static com.lab4.demo.report.ReportType.CSV;
import static com.lab4.demo.report.ReportType.PDF;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ItemControllerTest extends BaseControllerTest {

    @InjectMocks
    private ItemController controller;

    @Mock
    private ItemService itemService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private CSVReportService csvReportService;

    @Mock
    private PdfReportService pdfReportService;


    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new ItemController(itemService, reportServiceFactory);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allItems() throws Exception {
        List<ItemDTO> items = TestCreationFactory.listOf(Item.class);
        when(itemService.findAll()).thenReturn(items);

        ResultActions response = mockMvc.perform(get(ITEMS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(items));

    }

    @Test
    void create() throws Exception {
        ItemDTO reqItem = TestCreationFactory.newItemDTO();

        when(itemService.create(reqItem)).thenReturn(reqItem);

        ResultActions result = performPostWithRequestBody(ITEMS, reqItem);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void edit() throws Exception {
        long id = randomLong();
        ItemDTO reqItem = TestCreationFactory.newItemDTO();

        when(itemService.edit(id, reqItem)).thenReturn(reqItem);

        ResultActions result = performPutWithRequestBodyAndPathVariable(ITEMS + ENTITY, reqItem, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void getItem() throws Exception {
        long id = randomLong();
        ItemDTO reqItem = TestCreationFactory.newItemDTO();
        when(itemService.get(id)).thenReturn(reqItem);

        ResultActions result = performGetWithPathVariable(ITEMS + ENTITY, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void delete() throws Exception {
        long id = randomLong();
        doNothing().when(itemService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(ITEMS + ENTITY, id);
        verify(itemService, times(1)).delete(id);

        result.andExpect(status().isOk());

    }

}