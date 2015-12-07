package lucene.main.dataset;

import java.util.ArrayList;
import java.util.Arrays;


public class Data {
    private String newsNum = "";
    private String title = "";
    private String date = "";
    private String url = "";
    private String source = "";
    private String body = "";

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private ArrayList<Comments> commentsArr;
    private ArrayList<String> tags;
    private ArrayList<String> category;

    @Override
    public String toString() {
        return "Data{" +
                "newsNum='" + newsNum + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", url='" + url + '\'' +
                ", source='" + source + '\'' +
                ", body='" + body + '\'' +
                ", tags=" + tags +
                ", category=" + category +
                '}';
    }

    public ArrayList<String> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<String> category) {
        this.category = category;
    }

    public Data() {}



    public String getNewsNum() {
        return newsNum;
    }

    public void setNewsNum(String newsNum) {
        this.newsNum = newsNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String data) {
        this.date = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ArrayList<Comments> getCommentsArr() {
        return commentsArr;
    }

    public void setCommentsArr(ArrayList<Comments> commentsArr) {
        this.commentsArr = commentsArr;
    }



    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}
