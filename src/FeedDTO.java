public class FeedDTO {
    private int feedid;
    private String title;
    private String decription;
    private String link;

    public int getFeedid() {
        return feedid;
    }

    public void setFeedid(int feedid) {
        this.feedid = feedid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "FeedDTO{" +
                "feedid=" + feedid +
                ", title='" + title + '\'' +
                ", decription='" + decription + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
