package com.lab4.demo.report;

import com.lab4.demo.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;

import static com.lab4.demo.UrlMapping.ITEMS;
import static com.lab4.demo.UrlMapping.EXPORT_REPORT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ReportServiceFactoryTest extends BaseControllerTest {

    @Test
    void getReportService() throws Exception {
        ResultActions result = performGetWithPathVariable(ITEMS + EXPORT_REPORT, "PDF");
        result.andExpect(status().isOk());

        ResultActions result2 = performGetWithPathVariable(ITEMS + EXPORT_REPORT, "CSV");
        result2.andExpect(status().isOk());
    }
}