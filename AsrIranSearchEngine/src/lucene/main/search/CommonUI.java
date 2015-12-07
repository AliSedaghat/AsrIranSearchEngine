package lucene.main.search;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public abstract class CommonUI {

    // this function remove the old Index if exists
    public abstract void cleanIndex();

    // this function create the Index and put it in indexPath
    public abstract void makeIndex(String indexPath, String corePath, String labelPath, String categoryPath, String commentPath);

    // this function return list of URLs by search in title of the news
    public abstract ArrayList<String> getURLListByTitleSearch(String query) throws IOException, ParseException;

    // this function return list of URLs by search in title of the news
    public abstract ArrayList<String> getURLListByBodySearch(String query) throws IOException, ParseException;

    // this function return list of URLs by search in body of the news and Date filter on News
    public abstract ArrayList<String> getURLListByBodySearchAndDateRange(String query, int start, int end) throws IOException;


    // this function return id of comments by search in body of the comment and Date filter on News
    public abstract ArrayList<String> getCommentIDByCommentBodySearchAndDateRange(String query, int start, int end) throws IOException;

    // this function return list of URLS by search in body of the comment and Date filter on Comments
    public abstract ArrayList<String> getURLListByCommentBodySearchAndDateRange(String query, int start, int end) throws IOException;

    // List of URLS by label
    public abstract ArrayList<String> getURLListByLabel(ArrayList<String> labels) throws IOException;

    // search Comments by commenter Wild card query should be supported
    public abstract ArrayList<String> getCommentIDByCommneterWildCardQuery(String wildCardQuery) throws IOException;

    // return URL list by applying filter on
    // all method arguments must NOT be null

    public abstract ArrayList<String> getURLListByCommpoundSearch(ArrayList<String> labels, ArrayList<String> categories,
                                                                  String newsBodyQuery, String commentBodyQuery, int startNewsDate, int endNewsDate) throws IOException;
}