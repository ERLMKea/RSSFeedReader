import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {


    public static void getNews(JDBCWriter jdbcWriter) {
        ArrayList<RSSUrl> rss = RSSUrl.getCNN();
        boolean hasCon = jdbcWriter.setConnection();

        int count = jdbcWriter.countFromTable("feeds");
        System.out.println("feeds=" + count);

        int countmsg = jdbcWriter.countFromTable("feedmessages");
        System.out.println("feedmessages=" + countmsg);

        if (hasCon) {

            for (RSSUrl rssurl : rss) {
                String url = rssurl.getRssurl();
                System.out.println(url);
                System.out.println(rssurl.getId());
                RSSFeedParser parser = new RSSFeedParser(url);
                Feed feed = parser.readFeed();
                System.out.println(feed);
                jdbcWriter.writeFeed(rssurl, feed);
                for (FeedMessage message : feed.getMessages()) {
                    System.out.println(message);
                    jdbcWriter.writeFeedMessages(rssurl, feed, message);
                }
            }
        } else {
            System.out.println("surt ingen connect til database");
        }

        int aftercount = jdbcWriter.countFromTable("feeds");
        System.out.println("feeds=" + count);
        int aftercountmsg = jdbcWriter.countFromTable("feedmessages");
        System.out.println("feedmessages=" + countmsg);
        System.out.println("new feeds =" + (aftercount - count));
        System.out.println("new feedmessages =" + (aftercountmsg - countmsg));

    }


    public static void main(String[] args) {
        JDBCWriter jdbcWriter = new JDBCWriter();
        boolean hasCon = jdbcWriter.setConnection();

        if (hasCon) {
            getNews(jdbcWriter);
        };

        System.exit(1);


        int delcnt = jdbcWriter.deleteFeedMessage("xxx");
        System.out.println("delcnt=" + delcnt);

        ArrayList<FeedDTO> feeds = jdbcWriter.getFeeds("space");

        //int feedcnt = jdbcWriter.countFeedMessages("space");
        //System.out.println("feedcnt = " + feedcnt);

        //System.exit(1);
        //ArrayList<FeedDTO> feeds = new ArrayList<>();
        for (FeedDTO feed: feeds) {
            System.out.println(feed);

        }

    } //end main

}

