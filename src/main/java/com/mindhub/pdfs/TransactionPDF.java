package com.mindhub.pdfs;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.mindhub.models.Transaction;

public class TransactionPDF {

    private List<Transaction> transactions;

    public TransactionPDF(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Tipo", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Cuenta", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Descripción", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Fecha", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Monto", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Transaction transaction : transactions) {
            table.addCell(String.valueOf(transaction.getType()));
            table.addCell(transaction.getOwner_account().getNumber());
            table.addCell(transaction.getDescription());
            table.addCell(transaction.getDate());
            table.addCell(String.valueOf(transaction.getAmount()));
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setSize(18);


        Paragraph p = new Paragraph(transactions.get(0).getOwner_account().getOwner().getFirstName() + " " + transactions.get(0).getOwner_account().getOwner().getLastName()+ ": " + "Transacciones" , font);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.0f, 3.5f, 2.5f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
