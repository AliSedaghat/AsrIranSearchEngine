package lucene.main.search;


import lucene.main.dataset.Data;
import lucene.main.dataset.Read;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Indexer {



    public void createIndex(String indexPath, String corePath, String labelPath, String categoryPath ) throws IOException {
        Read read = new Read();
        read.readFile(corePath, labelPath, categoryPath);
        ArrayList<Data> data = read.getMainData();

        IndexWriterConfig indexWriterConf = new IndexWriterConfig(new StandardAnalyzer());
        indexWriterConf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        Directory fs = FSDirectory.open(Paths.get(indexPath));
        IndexWriter indexWriter = new IndexWriter(fs, indexWriterConf);

        FieldType fieldType = new FieldType();
        fieldType.setStored(true);
        fieldType.setStoreTermVectors(true);
        fieldType.setTokenized(true);
        fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);



        for (Data dataRow: data) {
            Document doc = new Document();

            doc.add(new StringField("newsNum", dataRow.getNewsNum(), Field.Store.NO));
            doc.add(new TextField("newsTitle", dataRow.getTitle(), Field.Store.NO));
            doc.add(new StringField("newsURL", dataRow.getUrl(), Field.Store.YES));
            doc.add(new StringField("newsDate", dataRow.getDate(), Field.Store.YES));
            doc.add(new Field("newsBody", dataRow.getBody(), fieldType));

            for (String str: dataRow.getTags()) {
                doc.add(new StringField("newsTags", str, Field.Store.NO));
            }
            String tags = "";
            for (String tag: dataRow.getTags()) {
                tags += tag + " ";
            }

            doc.add(new TextField("newsTags", tags, Field.Store.NO));

            String categories = "";
            for (String category: dataRow.getCategory()) {
                categories += category + " ";
            }
            doc.add(new TextField("newsCategories", categories, Field.Store.YES));


            indexWriter.addDocument(doc);
        }
        indexWriter.close();
    }


    public void createCommentIndex(String indexPath, String commentPath) throws IOException {
        FileInputStream input = new FileInputStream(new File(commentPath));
        XSSFWorkbook workbook = new XSSFWorkbook(input);
        XSSFSheet sheet = workbook.getSheetAt(0);

        IndexWriterConfig indexWriterConf = new IndexWriterConfig(new StandardAnalyzer());
        Directory fs = FSDirectory.open(Paths.get(indexPath));
        IndexWriter indexWriter = new IndexWriter(fs, indexWriterConf);

        String simpleDateFormat;
        String[] dateDetails;
        int year, month, day, sumOfYMD;

        for (Row row: sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }

            Document doc = new Document();

            for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
                if (row.getCell(i) != null) {
                    if (i == 5) {
                        row.getCell(i).setCellType(Cell.CELL_TYPE_NUMERIC);
                        Date javaDate= DateUtil.getJavaDate(row.getCell(i).getNumericCellValue());
                        simpleDateFormat = new SimpleDateFormat("MM/dd/yy").format(javaDate);
                        dateDetails = simpleDateFormat.split("/");
                        year = Integer.parseInt(dateDetails[2]);
                        month = Integer.parseInt(dateDetails[0]);
                        day = Integer.parseInt(dateDetails[1]);

                        sumOfYMD = year * 10000 + month * 100 + day;

                        doc.add(new StringField("comDate", sumOfYMD + "", Field.Store.YES));
                    }
                    row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
                    switch (i) {
                        case 0:
                            doc.add(new StringField("comId", row.getCell(i).getStringCellValue(), Field.Store.YES));
                            break;
                        case 1:
                            doc.add(new StringField("comParentId", row.getCell(i).getStringCellValue(), Field.Store.YES));
                            break;
                        case 2:
                            doc.add(new StringField("comURL", row.getCell(i).getStringCellValue(), Field.Store.YES));
                            break;
                        case 3:
                            doc.add(new StringField("commenter", row.getCell(i).getStringCellValue(), Field.Store.YES));
                            break;
                        case 4:
                            doc.add(new StringField("comLocation", row.getCell(i).getStringCellValue(), Field.Store.YES));
                            break;
                        case 6:
                            doc.add(new StringField("like", row.getCell(i).getStringCellValue(), Field.Store.YES));
                            break;
                        case 7:
                            doc.add(new StringField("disLike", row.getCell(i).getStringCellValue(), Field.Store.YES));
                            break;
                        case 8:
                            doc.add(new StringField("response", row.getCell(i).getStringCellValue(), Field.Store.NO));
                            break;
                        case 9:
                            doc.add(new TextField("comBody", row.getCell(i).getStringCellValue(), Field.Store.YES));
                            break;
                    }
                }
            }

            indexWriter.addDocument(doc);
        }

        indexWriter.close();

    }

}
