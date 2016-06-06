package sample.domain.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import sample.domain.models.Record;

public class RecordMapper {

    private static final DataFormatter CELL_FORMATTER = new DataFormatter();

    public static Record mapRecord(Row row) throws Exception {
        String title = escapeQuote(CELL_FORMATTER.formatCellValue(row.getCell(0)));
        String id = escapeQuote(CELL_FORMATTER.formatCellValue(row.getCell(1)));
        String provider = escapeQuote(CELL_FORMATTER.formatCellValue(row.getCell(2)));
        String certificate = escapeQuote(CELL_FORMATTER.formatCellValue(row.getCell(3)));
        String date = escapeQuote(CELL_FORMATTER.formatCellValue(row.getCell(4)));
        String description = escapeQuote(CELL_FORMATTER.formatCellValue(row.getCell(5)));
        return new Record(id, title, provider, certificate, date, description);
    }

    public static void fillRow(Record record, Row row) throws Exception {
        row.createCell(0).setCellValue(record.getTitle());
        row.createCell(1).setCellValue(record.getId());
        row.createCell(2).setCellValue(record.getProvider());
        row.createCell(3).setCellValue(record.getCertificate());
        row.createCell(4).setCellValue(record.getDate());
        row.createCell(5).setCellValue(record.getDescription());
    }

    private static String escapeQuote(String data) {
        return data.replaceAll("\'", "\"").trim();
    }
}
