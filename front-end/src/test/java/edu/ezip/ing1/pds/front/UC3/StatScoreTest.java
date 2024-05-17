package edu.ezip.ing1.pds.front.UC3;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.io.File;

public class StatScoreTest {

    @Disabled
    @DisplayName("Test d'exportation des données vers un fichier Excel")
    public void testExportData() {
        // Given
        String outputDir = "/Users/sophia";
        StatScore statScore = new StatScore();

        // When
        statScore.exportData();

        // Then
        File file = new File(outputDir + "/score.xls");
        assertTrue(file.exists());

        //  Assertion to check the content of the Excel file
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming the first sheet is used

            // Check the headers
            Row headerRow = sheet.getRow(0);
            assertEquals("", headerRow.getCell(0).getStringCellValue());
            assertEquals("2023-05", headerRow.getCell(1).getStringCellValue());


            // Check the data
            assertEquals("A", sheet.getRow(1).getCell(0).getStringCellValue());


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
