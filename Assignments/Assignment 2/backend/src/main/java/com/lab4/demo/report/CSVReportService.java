package com.lab4.demo.report;

import com.lab4.demo.item.model.dto.ItemDTO;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.report.ReportType.CSV;

@Service
public class CSVReportService implements ReportService {
    @Override
    public void export(List<ItemDTO> IDL) {

        File file = new File("src/main/resources/outOfStockBooks.csv");

        try {
            FileWriter outputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputFile);

            List<String[]> data = new ArrayList<String[]>();
            data.add(new String[] {"Title", "Author", "Genre", "Price", "Quantity"});
            for (ItemDTO item : IDL){
                data.add(new String[] {item.getTitle(), item.getAuthor(), item.getGenre(),
                        Float.toString(item.getPrice()), Float.toString(item.getQuantity())});
            }

            writer.writeAll(data);

            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
