package lucene.main.search;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;


import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class SearchEngine extends CommonUI {


    private IndexSearcher searcher;
    private QueryParser queryParser;
    private String indexDirPath = "indexDirectory";
    private String comIndexDirPath = "comIndexDirectory";
    private Indexer indexer = new Indexer();
    private File file = new File("file.txt");

    public String getIndexDirPath() {
        return indexDirPath;
    }

    @Override
    public void cleanIndex() {
        File indexFile = new File(indexDirPath);
        File comIndexFile = new File(comIndexDirPath);

        if (indexFile.exists() && comIndexFile.exists()) {
            removeDirectory(indexFile);
            removeDirectory(comIndexFile);
        }
    }


    @Override
    public void makeIndex
            (String indexPath, String corePath, String labelPath,
             String categoryPath, String commentPath) {
        try {
            if (indexPath.equals("") && !(new File(indexDirPath).exists()
                && !(new File(comIndexDirPath).exists()))) {

                indexer.createIndex(indexDirPath, corePath, labelPath, categoryPath);
                indexer.createCommentIndex(comIndexDirPath, commentPath);

            } else {
                indexDirPath = indexPath;
                comIndexDirPath = "comment" + indexPath;
                if (!(new File(indexDirPath).exists()) && !(new File(comIndexDirPath).exists())) {
                    indexer.createIndex(indexDirPath, corePath, labelPath, categoryPath);
                    indexer.createCommentIndex(comIndexDirPath, commentPath);
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public ArrayList<String> getURLListByTitleSearch(String queryString)
            throws IOException, ParseException {
        return generalSearch(queryString, "newsTitle", "newsURL", indexDirPath);
    }

    @Override
    public ArrayList<String> getURLListByBodySearch(String queryString)
            throws IOException, ParseException {
        ArrayList<String> arrayList;
        arrayList = generalSearch(queryString, "newsBody", "newsURL", indexDirPath);
        return arrayList;
    }

    @Override
    public ArrayList<String> getURLListByBodySearchAndDateRange(String queryString,
                                                                int start, int end)
            throws IOException {

        return generalSearchByDate
                (queryString, "newsBody", "newsDate", "newsURL", start, end, indexDirPath);

    }

    @Override
    public ArrayList<String> getCommentIDByCommentBodySearchAndDateRange
            (String queryString, int start, int end)
            throws IOException {

        return generalSearchByDate(queryString, "comBody", "comDate", "comId",
                start, end, comIndexDirPath);
    }

    @Override
    public ArrayList<String> getURLListByCommentBodySearchAndDateRange
            (String queryString, int start, int end)
            throws IOException {
        return generalSearchByDate
                (queryString, "comBody", "comDate", "comURL", start, end, comIndexDirPath);
    }

    @Override
    public ArrayList<String> getURLListByLabel(ArrayList<String> labels)
            throws IOException {
        String tags = "";

        for (String label : labels) {
            tags += label + " ";

        }


        return generalSearch(tags, "newsTags", "newsURL", indexDirPath);
    }

    @Override
    public ArrayList<String> getCommentIDByCommneterWildCardQuery(String wildCardQuery)
            throws IOException {

        return generalSearch(wildCardQuery, "commenter", "comId", comIndexDirPath);

    }


    @Override
    public ArrayList<String> getURLListByCommpoundSearch
            (ArrayList<String> labels, ArrayList<String> categories,
             String newsBodyQuery, String commentBodyQuery,
             int startNewsDate, int endNewsDate)
            throws IOException {

        ArrayList<String> resultArr = new ArrayList<>();

        System.out.println("Number of hits for commentBodyQuery: ");
        ArrayList<String> comURLList = generalSearch(commentBodyQuery, "comBody", "comURL", comIndexDirPath);
        Set<String> set = new HashSet<>(comURLList);         // To delete duplicate URLs

        searcher = new IndexSearcher(DirectoryReader.open
                (FSDirectory.open(Paths.get(indexDirPath))));

        String tags = "";
        for (String label : labels) {
            tags += label + " ";
        }

        String catG = "";
        for (String category : categories) {
            catG += category + " ";
        }

        String queryString = "newsTags:" + tags + "newsCategories:" + catG + "newsBody:"
                + newsBodyQuery + " AND newsDate:[" + startNewsDate + " TO " + endNewsDate + "]";
        try {
            QueryParser queryParser = new QueryParser("", new StandardAnalyzer());
            Query query = queryParser.parse(queryString);


            TopDocs topDocs = searcher.search(query, 500);
            ScoreDoc[] hits = topDocs.scoreDocs;
            int k = 0;
            
            for (ScoreDoc hit : hits) {
                Document doc = searcher.doc(hit.doc);
                if (set.contains(doc.get("newsURL"))) {
                    resultArr.add(doc.get("newsURL"));
                    k++;
                }
            }
            System.out.println("Number of hits for compound Search: ");
            System.out.println("Number of hits " + k);

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return resultArr;
    }


    private ArrayList<String> generalSearch(String queryString, String searchOn,
                                            String searchResultOn, String indexType)
            throws IOException {

        ArrayList<String> resultArr = new ArrayList<>();
        try {
            searcher = new IndexSearcher(DirectoryReader.open
                    (FSDirectory.open(Paths.get(indexType))));
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(indexType)));

            queryParser = new QueryParser(searchOn, new StandardAnalyzer());
            Query query = queryParser.parse(queryString);

            TopDocs topDocs = searcher.search(query, 500);
            ScoreDoc[] hits = topDocs.scoreDocs;
            System.out.println("number of hits " + hits.length);
            for (ScoreDoc hit : hits) {
                Document doc = searcher.doc(hit.doc);
                resultArr.add(doc.get(searchResultOn));
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return resultArr;
    }

    private ArrayList<String> generalSearchByDate
            (String queryString, String searchOn, String dateType,
             String searchResultOn, int start, int end, String indexType)
            throws IOException {

        ArrayList<String> resultArr = new ArrayList<>();

        try {
            QueryParser queryParser = new QueryParser(searchOn, new StandardAnalyzer());
            Query query = queryParser.parse(queryString);
            searcher = new IndexSearcher(DirectoryReader.open
                    (FSDirectory.open(Paths.get(indexType))));

            TopDocs topDocs = searcher.search(query, 500);
            ScoreDoc[] hits = topDocs.scoreDocs;
            int k = 0;
            for (ScoreDoc hit : hits) {

                Document doc = searcher.doc(hit.doc);

                if (doc.get(dateType).compareTo(start + "") >= 0 &&
                        doc.get(dateType).compareTo(end + "") <= 0) {
                    resultArr.add(doc.get(searchResultOn));
                    k++;
                }
            }
            System.out.println("number of hits " + k);



        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return resultArr;
    }





    public boolean removeDirectory(File directory) {

        if (directory == null)
            return false;
        if (!directory.exists())
            return true;
        if (!directory.isDirectory())
            return false;

        String[] list = directory.list();

        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                File entry = new File(directory, list[i]);

                if (entry.isDirectory())
                {
                    if (!removeDirectory(entry))
                        return false;
                }
                else
                {
                    if (!entry.delete())
                        return false;
                }
            }
        }

        return directory.delete();
    }

}