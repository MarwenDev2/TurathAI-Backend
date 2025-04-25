package pi.turathai.turathaibackend.Services;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.Itinery;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ExportService {

    @Autowired
    private ItenaryService itenaryService;

    public byte[] exportItinerariesToPdf() throws DocumentException, IOException {
        List<Itinery> itineraries = itenaryService.getAll();
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Add title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Itineraries Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Create table with 5 columns
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Table headers
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            String[] headers = {"ID", "Description", "Start Date", "End Date", "Budget"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setPadding(5);
                table.addCell(cell);
            }

            // Table data with null checks
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            for (Itinery itinery : itineraries) {
                // ID
                addCell(table, String.valueOf(itinery.getId()), dataFont);

                // Description
                addCell(table, itinery.getDescription() != null ? itinery.getDescription() : "", dataFont);

                // Start Date
                String startDate = itinery.getStartDate() != null ?
                        dateFormat.format(itinery.getStartDate()) : "N/A";
                addCell(table, startDate, dataFont);

                // End Date
                String endDate = itinery.getEndDate() != null ?
                        dateFormat.format(itinery.getEndDate()) : "N/A";
                addCell(table, endDate, dataFont);

                // Budget - assuming budget is primitive int (can't be null)
                addCell(table, "$" + itinery.getBudget(), dataFont);
            }

            document.add(table);
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }
        return out.toByteArray();
    }

    // Helper method to add cells with consistent formatting
    private void addCell(PdfPTable table, String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setPadding(5);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
    }
    public byte[] exportItinerariesToExcel() throws IOException {
        List<Itinery> itineraries = itenaryService.getAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Itineraries");

        // Create header row
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        String[] columns = {"ID", "Description", "Start Date", "End Date", "Budget"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        // Fill data rows
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int rowNum = 1;
        for (Itinery itinery : itineraries) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(itinery.getId());
            row.createCell(1).setCellValue(itinery.getDescription());

            // Handle null start date
            if (itinery.getStartDate() != null) {
                row.createCell(2).setCellValue(dateFormat.format(itinery.getStartDate()));
            } else {
                row.createCell(2).setCellValue("N/A");
            }

            // Handle null end date
            if (itinery.getEndDate() != null) {
                row.createCell(3).setCellValue(dateFormat.format(itinery.getEndDate()));
            } else {
                row.createCell(3).setCellValue("N/A");
            }

            row.createCell(4).setCellValue(itinery.getBudget());
        }

        // Adjust column width
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return out.toByteArray();
    }
}