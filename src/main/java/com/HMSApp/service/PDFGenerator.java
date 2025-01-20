package com.HMSApp.service;

import com.HMSApp.entity.Booking;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

@Service
public class PDFGenerator {

    public void generatePdf(String path, Booking booking) {
        try {
            // Create a new document
            Document document = new Document();

            // Initialize PdfWriter
            PdfWriter.getInstance(document, new FileOutputStream(path));

            // Open the document
            document.open();

            // Add a title to the document
            document.add(new Paragraph("Booking Details"));
            document.add(new Paragraph(" ")); // Add a blank line for spacing

            // Create a table with two columns
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100); // Set table width to 100% of the page

            // Add table headers
            table.addCell("Name");
            table.addCell("Mobile No.");

            // Add data to the table
            table.addCell(booking.getGuestName());
            table.addCell(booking.getMobile());

            // Add the table to the document
            document.add(table);

            // Close the document
            document.close();

            System.out.println("PDF created successfully at: " + path);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error creating PDF: " + e.getMessage());
        }
    }
}
