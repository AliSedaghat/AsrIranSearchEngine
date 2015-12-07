package lucene.main;
import lucene.main.search.SearchEngine;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.ArrayList;


public class Tester {
    SearchEngine commonUI;
    public void setUp() throws IOException {
        commonUI = new SearchEngine();
        commonUI.cleanIndex();
        commonUI.makeIndex("","core.xlsx","label.xlsx","category.xlsx","comments.xlsx");
    }
    public void checkGetURLListByTitleSearch() throws IOException, ParseException {
        ArrayList<String> titleSearchResults = commonUI.getURLListByTitleSearch("وزارت");
        if(titleSearchResults == null || !titleSearchResults.contains("http://www.asriran.com/fa/news/99206/"))
            System.err.print("checkGetURLListByTitleSearch failed \n");

    }
    public void checkGetURLListByBodySearch() throws IOException, ParseException {
        ArrayList<String> bodySearchResults = commonUI.getURLListByBodySearch("وزارت");
        if(bodySearchResults == null ||
                !bodySearchResults.contains("http://www.asriran.com/fa/news/414479/"))
            System.err.print("checkGetURLListByBodySearch failed \n");

    }
    public void checkGetURLListByBodySearchAndDateRange() throws IOException {
        ArrayList<String> bodyAndDateSearchResults = commonUI.getURLListByBodySearchAndDateRange("وزارت",141220,150101);
        if(bodyAndDateSearchResults == null ||
                !bodyAndDateSearchResults.contains("http://www.asriran.com/fa/news/372916/"))
            System.err.print("checkGetURLListByBodySearchAndDateRange failed \n");
    }
    public void checkGetURLListByCommentBodySearchAndDateRange() throws IOException {
        ArrayList<String> commentBodyAndDateSearchResults = commonUI.getURLListByCommentBodySearchAndDateRange("تهران",101101,120509);
        if(commentBodyAndDateSearchResults == null ||
                !commentBodyAndDateSearchResults.contains("http://www.asriran.com/fa/news/171008/"))
            System.err.print("checkGetURLListByCommentBodySearchAndDateRange failed \n");
    }
    public void checkGetCommentIDByCommentBodySearchAndDateRange() throws IOException {
        ArrayList<String> commentBodyAndDateFilterOnNewsResults = commonUI.getCommentIDByCommentBodySearchAndDateRange("تهران",110702,110702);
        if(commentBodyAndDateFilterOnNewsResults == null ||
                !commentBodyAndDateFilterOnNewsResults.contains("922"))
            System.err.print("checkGetCommentIDByCommentBodySearchAndDateRange failed \n");
    }
    public void checkGetCommentIDCommenterWildCardQuery() throws IOException {
        ArrayList<String> wildCardQueryResults = commonUI.getCommentIDByCommneterWildCardQuery("عل*");
        if(wildCardQueryResults == null ||
                !wildCardQueryResults.contains("1128"))
            System.err.print("checkGetCommentIDCommenterWildCardQuery failed \n");
    }
    public void checkGetURLListByLabel() throws IOException {
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("سکته مغزی");
        labels.add("دکتر نیکنام");
        ArrayList<String> labelSearchResults = commonUI.getURLListByLabel(labels);
        if(labelSearchResults == null ||
                !labelSearchResults.contains("http://www.asriran.com/fa/news/420113/"))
            System.err.print("checkGetURLListByLabel failed \n");
    }
    public void checkGetURLListByCompoundSearch() throws IOException {
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("سکته مغزی");
        labels.add("دکتر نیکنام");
        ArrayList<String> categories = new ArrayList<String>();
        categories.add("اجتماعی");
        ArrayList<String> compoundQueryResults = commonUI.getURLListByCommpoundSearch(labels,categories,"اعصاب","رحمت",150928,150930);

        if(compoundQueryResults == null ||
                !compoundQueryResults.contains("http://www.asriran.com/fa/news/420113/"))
            System.err.print("checkGetURLListByCompoundSearch failed \n");
    }

}
