import java.util.ArrayList;

public class RSSUrl {
    private int id;
    private String rssurl;

    public static ArrayList<RSSUrl> getNews() {
        ArrayList<RSSUrl> rss = new ArrayList<>();
        rss.add(new RSSUrl(1, "https://www.bt.dk/musik/seneste/rss"));
        rss.add(new RSSUrl(2, "https://ekstrabladet.dk/rssfeed/biler/"));  //ekstrabladet.dk/rssfeed/biler/
        rss.add(new RSSUrl(3, "https://ekstrabladet.dk/rssfeed/kendte/"));
        rss.add(new RSSUrl(4, "https://ekstrabladet.dk/rssfeed/ferie/"));
        rss.add(new RSSUrl(5, "https://ekstrabladet.dk/rssfeed/Sex_og_samliv/"));
        return rss;
    }


    public static ArrayList<RSSUrl> getCNN() {
        ArrayList<RSSUrl> rss = new ArrayList<>();
        rss.add(new RSSUrl(11, "http://rss.cnn.com/rss/edition_technology.rss"));
        rss.add(new RSSUrl(12, "http://rss.cnn.com/rss/edition_space.rss"));
        rss.add(new RSSUrl(13, "http://rss.cnn.com/rss/edition.rss"));
        rss.add(new RSSUrl(14, "http://rss.cnn.com/rss/edition_sport.rss"));
        return rss;
    }

    public RSSUrl(int id, String rssurl) {
        this.id = id;
        this.rssurl = rssurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRssurl() {
        return rssurl;
    }

    public void setRssurl(String rssurl) {
        this.rssurl = rssurl;
    }
}
