package lucene.main.dataset;


public class Comments {
    private String id = "";
    private String parentId = "";
    private String commenter = "";
    private String location = "";
    private String date = "";
    private String like = "";
    private String disLike = "";
    private String responseCount = "";
    private String body = "";

    public Comments(String id, String parentId, String commenter, String location,
                    String date, String like, String disLike, String responseCount, String body) {
        this.id = id;
        this.parentId = parentId;
        this.commenter = commenter;
        this.location = location;
        this.date = date;
        this.like = like;
        this.disLike = disLike;
        this.responseCount = responseCount;
        this.body = body;
    }

    public Comments() {}

    public String getId() {
        return id;

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getDisLike() {
        return disLike;
    }

    public void setDisLike(String disLike) {
        this.disLike = disLike;
    }

    public String getResponseCount() {
        return responseCount;
    }

    public void setResponseCount(String responseCount) {
        this.responseCount = responseCount;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", commenter='" + commenter + '\'' +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                ", like='" + like + '\'' +
                ", disLike='" + disLike + '\'' +
                ", responseCount='" + responseCount + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
