package lucene.main.dataset;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Read {

    private ArrayList<Data> mainData = new ArrayList<>();

    public ArrayList<Data> getMainData() {
        return mainData;
    }

    public void readFile(String corePath, String labelPath, String categoryPath) throws IOException {


        FileInputStream inputCore = new FileInputStream(new File(corePath));
        XSSFWorkbook workbookCore = new XSSFWorkbook(inputCore);
        XSSFSheet sheetCore = workbookCore.getSheetAt(0);

        FileInputStream inputCategory = new FileInputStream(new File(categoryPath));
        XSSFWorkbook workbookCategory = new XSSFWorkbook(inputCategory);
        XSSFSheet sheetCategory = workbookCategory.getSheetAt(0);

        FileInputStream inputLabel = new FileInputStream(new File(labelPath));
        XSSFWorkbook workbookLabel = new XSSFWorkbook(inputLabel);
        XSSFSheet sheetLabel = workbookLabel.getSheetAt(0);

        String simpleDateFormat;
        String[] dateDetails;
        int year, month, day, sumOfYMD;

        for (Row row : sheetCore) {
            Data data = new Data();
            if (row.getRowNum() == 0) {
                continue;
            }

            for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
                if (i == 3) {
                    row.getCell(i).setCellType(Cell.CELL_TYPE_NUMERIC);
                    Date javaDate= DateUtil.getJavaDate(row.getCell(i).getNumericCellValue());
                    simpleDateFormat = new SimpleDateFormat("MM/dd/yy").format(javaDate);
                    dateDetails = simpleDateFormat.split("/");
                    year = Integer.parseInt(dateDetails[2]);
                    month = Integer.parseInt(dateDetails[0]);
                    day = Integer.parseInt(dateDetails[1]);

                    sumOfYMD = year * 10000 + month * 100 + day;
                    data.setDate(sumOfYMD + "");


                } else if (row.getCell(i) != null) {
                    row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
                    switch (i) {
                        case 0:
                            data.setNewsNum(row.getCell(0).getStringCellValue());
                            break;
                        case 1:
                            data.setTitle(row.getCell(1).getStringCellValue());
                            break;
                        case 2:
                            data.setBody(row.getCell(2).getStringCellValue());
                            break;
                        case 4:
                            data.setUrl(row.getCell(4).getStringCellValue());
                            break;
                        case 5:
                            data.setSource(row.getCell(5).getStringCellValue());
                            break;

                    }
                } else {
                    switch (i) {
                        case 1:
                            data.setTitle("");
                            break;
                        case 2:
                            data.setBody("");
                            break;
                    }
                }

            }





            ArrayList<String> category = new ArrayList<>();
            for (Row rowCategory: sheetCategory) {
                if (rowCategory.getRowNum() == 0) {
                    continue;
                }
                if (row.getCell(4).getStringCellValue().equals
                        (rowCategory.getCell(0).getStringCellValue())) {
                    if (rowCategory.getCell(2) != null) {

                        category.add(rowCategory.getCell(2).getStringCellValue());
                    } else {
                        category.add("");
                    }

                }

            }
            data.setCategory(category);


            ArrayList<String> tagArr = new ArrayList<>();
            for (Row rowLabel: sheetLabel) {
                if (rowLabel.getRowNum() == 0) {
                    continue;
                }

                if (rowLabel.getCell(0).getStringCellValue().equals
                        (row.getCell(4).getStringCellValue())) {

                    if (rowLabel.getCell(2) != null) {
                        rowLabel.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                        tagArr.add(rowLabel.getCell(2).getStringCellValue());


                    } else {
                        tagArr.add("");
                    }
                }
            }

            data.setTags(tagArr);
            mainData.add(data);

        }

        inputCore.close();
        inputCategory.close();
        inputLabel.close();
    }




}
