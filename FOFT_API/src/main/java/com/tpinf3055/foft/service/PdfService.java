package com.tpinf3055.foft.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.List;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;


@Service
public class PdfService {

        public void export(HttpServletResponse response) throws IOException {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());


            document.open();
//            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
//            fontTitle.setSize(20);
//            fontTitle.setColor(Color.pink);
//
//            Paragraph paragraph = new Paragraph("Fiche De Suivi", fontTitle);
//            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
//
//            Font row = new RowFilter<>()
//            Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
//            fontParagraph.setSize(12);
//
//            Paragraph paragraph2 = new Paragraph("This is a paragraph.", fontParagraph);
//            paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
//
//            document.add(paragraph);
//            document.add(paragraph2);
            PdfPTable table = new PdfPTable(3);
            addTableHeader(table);
            addRows(table);


            document.add(table);
            document.close();


        }

    private void addRows(PdfPTable table) {
        table.addCell("row 1, col 1");
        table.addCell("row 1, col 2");
        table.addCell("row 1, col 3");
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("column header 1")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(Color.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setRowspan(3);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addCustomRows(PdfPTable table)
            throws URISyntaxException, BadElementException, IOException {
        Path path = Paths.get(ClassLoader.getSystemResource("Java_logo.png").toURI());
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        img.scalePercent(10);

        PdfPCell imageCell = new PdfPCell(img);
        table.addCell(imageCell);

        PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("row 2, col 2"));
        horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(horizontalAlignCell);

        PdfPCell verticalAlignCell = new PdfPCell(new Phrase("row 2, col 3"));
        verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(verticalAlignCell);
    }
}
