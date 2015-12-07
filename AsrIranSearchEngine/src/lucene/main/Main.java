package lucene.main;


import lucene.main.search.SearchEngine;
import org.apache.lucene.queryparser.classic.ParseException;
import java.io.IOException;


public class Main  {

    public static void main(String[] args) {
        try {
            Tester tester = new Tester();
            tester.setUp();
            //tester.checkGetURLListByLabel();
            //tester.checkGetURLListByTitleSearch();
            //tester.checkGetCommentIDCommenterWildCardQuery();
            //tester.checkGetURLListByBodySearch();
            //tester.checkGetURLListByCompoundSearch();
            //tester.checkGetURLListByBodySearchAndDateRange();
            //tester.checkGetURLListByCommentBodySearchAndDateRange();
            //tester.checkGetCommentIDByCommentBodySearchAndDateRange();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
