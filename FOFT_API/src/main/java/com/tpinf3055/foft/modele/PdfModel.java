package com.tpinf3055.foft.modele;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfTable;

import java.awt.*;

public class PdfModel {
    private void writeTableHeader(PdfTable table){



        PdfPCell pdfPCell = new PdfPCell();
        pdfPCell.setBackgroundColor(Color.cyan);
        pdfPCell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.white);

    }
}
