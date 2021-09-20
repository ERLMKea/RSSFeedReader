import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main2 {

    public static void main(String[] args) {
        // write your code here
        JDBCWriter jdbc = new JDBCWriter();
        Boolean res = jdbc.setConnection();
        System.out.println("Vi har connection=" + res);

        int cnt = jdbc.countCustomers();
        System.out.println("der er cnt=" + cnt);

        //String rssurl = "https://www.bt.dk/musik/seneste/rss";
        //String rssurl = "https://www.bt.dk/musik/seneste/rss";
        //String rssurl = "https://www.tv2lorry.dk/rss";
        //String ebbiler = "https://ekstrabladet.dk/rssfeed/biler/";
        String cnnafrica = "https://rss.nytimes.com/services/xml/rss/nyt/Africa.xml";

        RSSFeedParser parser = new RSSFeedParser(cnnafrica);

        Feed feed = parser.readFeed();
        System.out.println(feed);

        //kun title link og pubDate ser interessante ud. nej også language og copyright
        System.out.println("title=" + feed.title); //ok
        System.out.println("link=" + feed.link); //ok
        System.out.println("descr=" + feed.description); //blank
        System.out.println("language=" + feed.language); //ok da
        System.out.println("copyright=" + feed.copyright); //blank
        System.out.println("pubDate=" + feed.pubDate); //ok

        System.out.println("STARTER Messages");
        for (FeedMessage message : feed.getMessages()) {
            System.out.println("MESSAGE");
            System.out.println(message);
            System.out.println("title=" + message.title);
            System.out.println("descript=" + message.description);
            System.out.println("link=" + message.link);
            System.out.println("Author=" + message.author);
            System.out.println("guid=" + message.guid);
        }


    }

}


