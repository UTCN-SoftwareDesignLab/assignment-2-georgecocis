package com.lab4.demo.report;

import com.lab4.demo.item.model.dto.ItemDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.lab4.demo.report.ReportType.PDF;

@Service
public class PdfReportService implements ReportService {
    @Override
    public void export(List<ItemDTO> IDL) throws IOException {

        try (PDDocument pdfDoc = new PDDocument()) {

            PDPage myPage = new PDPage();
            pdfDoc.addPage(myPage);

            try (PDPageContentStream cont = new PDPageContentStream(pdfDoc, myPage)) {

                cont.beginText();
                cont.setFont(PDType1Font.TIMES_ROMAN, 30);
                cont.setLeading(14.5f);
                cont.newLineAtOffset(25, 700);

                cont.showText("Out of stock books report");
                cont.setFont(PDType1Font.TIMES_ROMAN, 12);
                cont.newLine();
                cont.newLine();

                for (ItemDTO item : IDL){
                    cont.showText("Title: " + item.getTitle());
                    cont.newLine();

                    cont.showText("Author: " + item.getAuthor());
                    cont.newLine();

                    cont.showText("Genre: " + item.getGenre());
                    cont.newLine();

                    cont.showText("Price: " + item.getPrice());
                    cont.newLine();

                    cont.showText("Quantity: " + item.getQuantity());
                    cont.newLine();
                    cont.newLine();
                }
                cont.endText();
            }
            pdfDoc.save("src/main/resources/outOfStockBooks.pdf");
        }
    }


    @Override
    public ReportType getType() {
        return PDF;
    }
}
