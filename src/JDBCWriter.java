import java.sql.*;
import java.util.ArrayList;

public class JDBCWriter {

    private Connection connection = null;

    public boolean setConnection() {
        final String url = "jdbc:mysql://localhost:3306/rssfeed01?serverTimezone=UTC";
        boolean bres = false;
        try {
            connection = DriverManager.getConnection(url, "furt", "x");
            bres = true;
        } catch (SQLException ioerr) {
            System.out.println("Vi fik IKKE connection=" + ioerr.getMessage());
        }
        return bres;
    }

    public int countFromTable(String Tablename) {
        //String countstr = "select count(*) from customers";
        String countstr = "select count(*) from " + Tablename;
        PreparedStatement preparedStatement;
        int res = -1;
        try {
            preparedStatement = connection.prepareStatement(countstr);
            ResultSet resset = preparedStatement.executeQuery();
            if (resset.next()) {
                String str = "" + resset.getObject(1);
                res = Integer.parseInt(str);
            };
        } catch (SQLException err) {
            System.out.println("FEJL i count=" + err.getMessage());
        }
        return res;
    }

    public int countCustomers() {
        String srcstr = "select count(*) from customers";
        PreparedStatement preparedStatement;
        int res = -1;
        try {
            preparedStatement = connection.prepareStatement(srcstr);
            ResultSet resset = preparedStatement.executeQuery();
            if (resset.next()) {
                String str = "" + resset.getObject(1);
                res = Integer.parseInt(str);
            };
        } catch (SQLException err) {
            System.out.println("FEJL i søg=" + err.getMessage());
        }
        return res;
    }



    public int countFeedMessages() {
        String srcstr = "select count(*) from feedmessages";
        PreparedStatement preparedStatement;
        int res = -1;
        try {
            preparedStatement = connection.prepareStatement(srcstr);
            ResultSet resset = preparedStatement.executeQuery();
            if (resset.next()) {
                String str = "" + resset.getObject(1);
                res = Integer.parseInt(str);
            };
        } catch (SQLException err) {
            System.out.println("FEJL i søg=" + err.getMessage());
        }
        return res;
    }

    public int deleteFeedMessage(String aWord) {
        String delstr = "delete from feedmessages where title like ?";
        PreparedStatement preparedStatement;
        int rowcount = 0;
        try {
            preparedStatement = connection.prepareStatement(delstr);
            preparedStatement.setString(1, "%" + aWord + "%");
            int ii = preparedStatement.executeUpdate();
            rowcount+=ii;
        } catch (SQLException err) {
            System.out.println("Fejl i Delete err=" + err.getMessage());
        }
        return rowcount;
    }

    public int writeFeed(RSSUrl rssurl, Feed feed) {
        String insstr = "INSERT INTO feeds(feedid, rssurl, title, link, language, pubdate) values (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement;
        int rowcount = 0;
            try {
                    preparedStatement = connection.prepareStatement(insstr);
                    preparedStatement.setInt(1, rssurl.getId());
                    preparedStatement.setString(2, rssurl.getRssurl());
                    preparedStatement.setString(3, feed.getTitle());
                    preparedStatement.setString(4, feed.getLink());
                    preparedStatement.setString(5, feed.getLanguage());
                    preparedStatement.setString(6, feed.getPubDate());
                    int ii = preparedStatement.executeUpdate();
                    rowcount+=ii;
            } catch (SQLException sqlerr) {
                System.out.println("sql fejl i writelines=" + sqlerr.getMessage());
            }
        System.out.println("færdig");
        return rowcount;
    }

    public int writeFeedMessages(RSSUrl rssurl, Feed feed, FeedMessage feedMessage) {
        String insstr = "INSERT INTO feedmessages(feedid, title, description, link, author, guid) values (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement;
        int rowcount = 0;
        try {
            preparedStatement = connection.prepareStatement(insstr);
            preparedStatement.setInt(1, rssurl.getId());
            preparedStatement.setString(2, feedMessage.getTitle());
            preparedStatement.setString(3, feedMessage.getDescription());
            preparedStatement.setString(4, feedMessage.getLink());
            preparedStatement.setString(5, feedMessage.getAuthor());
            preparedStatement.setString(6, feedMessage.getGuid());
            int ii = preparedStatement.executeUpdate();
            rowcount+=ii;
        } catch (SQLException sqlerr) {
            System.out.println("sql fejl i writelines=" + sqlerr.getMessage());
        }
        return rowcount;
    }

    public int countFeedMessages(String aWord) {
        String srcstr = "select f1.feedid, msg1.title, description, msg1.link from feeds f1 ";
        srcstr += " join feedmessages msg1 using(feedid) where f1.title like " + '"' + "%space%" + '"' + " or " + " msg1.title like " + '"' + "%space%" + '"';
        PreparedStatement preparedStatement;
        int res = 0;
        try {
            preparedStatement = connection.prepareStatement(srcstr);
            ResultSet resset = preparedStatement.executeQuery();
            while (resset.next()) {
                res++;
                String str = "" + resset.getObject(1);
                int feedid = Integer.parseInt(str);
                System.out.println("feedid=" + str);
            };
        } catch (SQLException err) {
            System.out.println("FEJL i søg=" + err.getMessage());
        }
        return res;
    }


    public ArrayList<FeedDTO> getFeeds(String aWord) {
        //select f1.feedid, msg1.title, description, msg1.link from feeds f1
        //join feedmessages msg1 using(feedid)
        //        where f1.title like "%space%" or msg1.title like "%space%"
        //order by pubdate;

        String srcstr = "select f1.feedid, msg1.title, description, msg1.link from feeds f1 ";
        srcstr += " join feedmessages msg1 using(feedid) where f1.title like ?  or  msg1.title like ?";

        System.out.println("srcstr = " + srcstr);
        PreparedStatement preparedStatement;
        ArrayList<FeedDTO> feeds = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(srcstr);
            preparedStatement.setString(1, "%" + aWord +"%");
            preparedStatement.setString(2, "%" + aWord +"%");
            System.out.println(srcstr);
            ResultSet resset =  preparedStatement.executeQuery();
            while (resset.next()) {
                FeedDTO feed = new FeedDTO();
                int feedid = resset.getInt(1);
                feed.setFeedid(feedid);
                String s1 = resset.getString(2);
                feed.setTitle(s1);
                s1 = resset.getString("description");
                feed.setDecription(s1);
                s1 = resset.getString("link");
                feed.setLink(s1);
                feeds.add(feed);
            }
        } catch (SQLException sqlerr) {
            System.out.println("Error in select=" + sqlerr.getMessage());
        }
        return feeds;
    }




    public ArrayList<FeedDTO> getFeedsErr(String aWord) {
        //select f1.feedid, msg1.title, description, msg1.link from feeds f1
        //join feedmessages msg1 using(feedid)
        //        where f1.title like "%space%" or msg1.title like "%space%"
        //order by pubdate;

        String searchStr = "SELECT f1.feedid, msg1.title, description, msg1.link FROM from feeds f1 ";
        //searchStr += " join feedmessages msg1 using(feedid) where like f1.title like \"%?%\" or msg1.title like \"%?% ";
        searchStr += " join feedmessages msg1 using(feedid) where like f1.title like ? or msg1.title like ? ";
        System.out.println("searchStr= " + searchStr);
        PreparedStatement preparedStatement;
        ArrayList<FeedDTO> feeds = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(searchStr);
            preparedStatement.setString(1, "%" + aWord +"%");
            preparedStatement.setString(2, "%" + aWord +"%");
            System.out.println(searchStr);
            ResultSet resset =  preparedStatement.executeQuery();
            while (resset.next()) {
                FeedDTO feed = new FeedDTO();
                int feedid = resset.getInt(1);
                feed.setFeedid(feedid);
                feeds.add(feed);
            }
        } catch (SQLException sqlerr) {
            System.out.println("Error in select=" + sqlerr.getMessage());
        }
        return feeds;
    }



}
