package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;

import java.util.zip.ZipInputStream;


public class FilesTests {

    private ClassLoader cl = FilesTests.class.getClassLoader();



    @Test
    void pdfTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("files.zip"))) {
            ZipEntry entry;

            boolean isFileFound = false;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("pdf_example.pdf")) {
                    isFileFound = true;
                    break;
                }
            }

            if (!isFileFound) {
                throw new FileNotFoundException("ERROR: PDF file not found in the zip archive");
            }

            try(ZipInputStream is = new ZipInputStream(cl.getResourceAsStream("files.zip"))){
                ZipEntry innerEntry;
                while((innerEntry = is.getNextEntry()) != null){
                    if(innerEntry.getName().equals("pdf_example.pdf")){
                        PDF pdf = new PDF(is);
                        Assertions.assertEquals("PDF Form Example", pdf.title);
                    }
                }
            }

        }

    }

    @Test
    void xlsxTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("files.zip"))) {
            ZipEntry entry;

            boolean isFileFound = false;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("xlsx_example.xlsx")) {
                    isFileFound = true;
                    break;
                }
            }

            if (!isFileFound) {
                throw new FileNotFoundException("ERROR: Xlsx file not found in the zip archive");
            }


            try(ZipInputStream is = new ZipInputStream(cl.getResourceAsStream("files.zip"))){
                ZipEntry innerEntry;
                while((innerEntry = is.getNextEntry()) != null){
                    if(innerEntry.getName().equals("xlsx_example.xlsx")){
                        XLS xls = new XLS(zis);
                        String actualValue = xls.excel.getSheetAt(0).getRow(1).getCell(11).getStringCellValue();
                        Assertions.assertNotNull(actualValue);
                    }
                }
            }

        }

    }

    @Test
    void csvTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("files.zip"))) {
            ZipEntry entry;

            boolean isFileFound = false;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("csv_example.csv")) {
                    isFileFound = true;
                    break;
                }
            }

            if (!isFileFound) {
                throw new FileNotFoundException("ERROR: Csv file not found in the zip archive");
            }

            try(ZipInputStream is = new ZipInputStream(cl.getResourceAsStream("files.zip"))){
                ZipEntry innerEntry;
                while((innerEntry = is.getNextEntry()) != null){
                    if(innerEntry.getName().equals("csv_example.csv")){
                        CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                        List<String[]> data = csvReader.readAll();

                        Assertions.assertEquals(5, data.size());
                        Assertions.assertArrayEquals(new String[]{"iivanova@service.ru", "iivanova"}, data.get(0));
                        Assertions.assertArrayEquals(new String[]{"ksergeev@service.ru", "ksergeev"}, data.get(1));
                    }
                }
            }

        }
    }

}
