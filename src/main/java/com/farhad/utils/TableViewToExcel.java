package com.farhad.utils;

import com.farhad.models.Transaction;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class TableViewToExcel {
    public static void write(List<Transaction> transactionList, String tableName) {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet(tableName);
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 10000);
        sheet.setColumnWidth(2, 10000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Account ID");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Destination Account ID");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Amount of Money");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        int index = 1;
        for (Transaction transaction : transactionList) {
            Row row = sheet.createRow(index);
            Cell cell = row.createCell(0);
            cell.setCellValue(transaction.getAccountId());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(transaction.getDestinationAccountId());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(transaction.getAmountOfTransaction());
            cell.setCellStyle(style);
            index++;
        }

        File currDir = getFile();
        if (currDir != null) {
            int length = currDir.toString().length();
            // *.xlsx --> length - 5, length
            String fileLocation = (currDir.toString().substring(length - 5, length).equals(".xlsx")) ?
                    currDir.getAbsolutePath() : currDir.getAbsolutePath() + ".xlsx";
            System.out.println(fileLocation);
            try (FileOutputStream outputStream = new FileOutputStream(fileLocation)) {
                workbook.write(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static File getFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("xlsx Files", "*.xlsx")
        );
        return fileChooser.showSaveDialog(null);
    }

    private static  <T> TableColumn<T, ?> getTableColumnByName(TableView<T> tableView, String name) {
        for (TableColumn<T, ?> col : tableView.getColumns())
            if (col.getText().equals(name)) return col;
        return null;
    }

}
