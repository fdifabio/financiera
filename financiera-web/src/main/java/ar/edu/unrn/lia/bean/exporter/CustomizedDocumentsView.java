package ar.edu.unrn.lia.bean.exporter;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.io.IOException;
import java.util.Iterator;

@Named
@Scope("view")
public class CustomizedDocumentsView {

    public void postProcessXLS(Object document) {
        XSSFWorkbook w = (XSSFWorkbook) document;
        XSSFSheet sheet = w.getSheetAt(0);
        XSSFRow header;

        int ultima = sheet.getLastRowNum() + 1;
        for (int filas = 3; filas < ultima; filas++) {
            header = sheet.getRow(filas);
            for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
                XSSFCell cell = header.getCell(i);
                if (cell != null && cell.getCellType() == cell.CELL_TYPE_STRING) {
                    if (cell.getStringCellValue().equalsIgnoreCase("true")) {
                        cell.setCellValue("ok");
                    } else if (cell.getStringCellValue().equalsIgnoreCase("false")) {
                        cell.setCellValue("x");
                    }

                }

            }
        }

    }

    public void postProcessXLSDocument(Object document) {

        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        Iterator<Row> rowIterator = sheet.iterator();

        if (rowIterator.hasNext()) {
            rowIterator.next();
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell.getColumnIndex() > 1) {
                    if (!cell.getStringCellValue().isEmpty() && cell.getStringCellValue().contains("$")) {
                        String number = cell.getStringCellValue();
                        number = number.replace("$", "");
                        number = number.replace(".", "");
                        number = number.replace(",", ".");
                        number = number.replace("(", "-");
                        number = number.replace(")", "");
                        cell.setCellValue(Double.valueOf(number));
                    }
                }
            }
        }
    }

    public void postProcessXLSDesembolsos(Object document) {
        XSSFWorkbook w = (XSSFWorkbook) document;
        XSSFSheet sheet = w.getSheetAt(0);
        XSSFRow header;

        int ultima = sheet.getLastRowNum() + 1;
        for (int filas = 3; filas < ultima; filas++) {
            header = sheet.getRow(filas);
            for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
                XSSFCell cell = header.getCell(i);
                if (cell != null && cell.getCellType() == cell.CELL_TYPE_STRING) {
                    if (cell.getStringCellValue().equalsIgnoreCase("true")) {
                        cell.setCellValue("ok");
                    } else if (cell.getStringCellValue().equalsIgnoreCase("false")) {
                        cell.setCellValue("x");
                    }
                }

            }
        }

    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);

    }
}
