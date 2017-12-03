package ru.lantimat.studprof;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import ru.lantimat.studprof.Feeds.Feed;
import ru.lantimat.studprof.Feeds.News;

import static ru.lantimat.studprof.SpRestClient.urlStudProf;

/**
 * Created by lAntimat on 02.12.2017.
 */

public class Repository {

    public static String urlFeed = "/feed/index/?page=";
    public static String urlNews = "/news/index/?page=";
    static int feedPage;
    static int newsPage;

    static ArrayList<Feed> arFeed = new ArrayList<>();
    static ArrayList<News> arNews = new ArrayList<>();

    public interface FeedCallback {
        void onSuccess(ArrayList<Feed> ar);
    }

    public interface NewsCallback {
        void onSuccess(ArrayList<News> ar);
    }

    public static void getFeeds(int page, final FeedCallback callback) {
        feedPage = page;

        SpRestClient.get(urlFeed + page, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseFeeds(responseBody, callback);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public static void getNews(int page, final NewsCallback callback) {
        newsPage = page;

        SpRestClient.get(urlNews + page, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseNews(responseBody, callback);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private static void parseFeeds(byte[] bytes, FeedCallback callback) {
        Document doc = null;//Здесь хранится будет разобранный html документ
        try {

            String str = new String(bytes, "utf-8");
            doc = Jsoup.parse(str);
        } catch (Exception e) {
            //Если не получилось считать
            e.printStackTrace();

        }

        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
        if (doc != null) {
            // задаем с какого места, я выбрал заголовке статей


            Elements listItemRow = doc.select("article.list-item.row");
            Elements listItemFixed = doc.select("article.list-item-fixed");

            Elements title = listItemRow.select("h3");
            Elements shortDescribe = listItemRow.select("p.mt10");
            Elements tvDate = listItemRow.select("span.list-item-subtitle");
            //picture = doc.select("img[src$=.jpg]");
            Elements picture = listItemRow.select("img");
            //profilePicture = doc.select("img");
            Elements feedUrl = listItemRow.select("a[title]");
            //[0]-верхняя/нижняя, [1]-дата
            Elements dateWeek = doc.select("div.top-date-week");

            Elements countOfVisit = listItemRow.select("div.span10.list-item-body");


            if (feedPage == 1) {
                //Парсим закрепленную запись
                parseEventListItemFixed(listItemFixed);
            }

            // и в цикле захватываем все данные какие есть на странице
            for (int i = 0; i < title.size(); i++) {

                String[] counts = countOfVisit.get(i).text().split(" ");
                String visitCount = counts[1];
                String commentsCount = counts[0];

                String feedUrlString = feedUrl.get(i).attr("href");
                String pictureBig = picture.get(i).attr("src");
                if (pictureBig.contains("min"))
                    pictureBig = pictureBig.replace("_min", "");
                arFeed.add(new Feed(title.get(i).text(), shortDescribe.get(i).text(), tvDate.get(i).text(),
                        pictureBig, feedUrlString, visitCount, commentsCount, false, Feed.NORMAL_TYPE));

            }
        }

        callback.onSuccess(arFeed);
    }

    private static void parseEventListItemFixed(Elements _listItemFixed) {

        try {
            Elements _title = _listItemFixed.select("h3");
            Elements _shortDescribe = _listItemFixed.select("p");
            //Elements _tvDate = _listItemFixed.select("span.list-item-subtitle");
            //picture = doc.select("img[src$=.jpg]");
            Elements _picture = _listItemFixed.select("img");
            //profilePicture = doc.select("img");
            Elements _feedUrl = _listItemFixed.select("a[href$=.html]");

            String feedUrlString = urlStudProf + _feedUrl.get(0).attr("href");
            arFeed.add(new Feed(_title.get(0).text(), _shortDescribe.get(0).text(), "Запись закреплена",
                    _picture.get(0).attr("src"), feedUrlString, "", "", false, Feed.FIXED_TYPE));
        } catch (Exception e) {

        }
    }

    private static void parseNews(byte[] bytes, NewsCallback callback) {
        Document doc = null;//Здесь хранится будет разобранный html документ
        try {

            String str = new String(bytes, "utf-8");
            doc = Jsoup.parse(str);
        } catch (Exception e) {
            //Если не получилось считать
            e.printStackTrace();

        }

        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
        if (doc != null) {
            // задаем с какого места, я выбрал заголовке статей


            Elements listItemRow = doc.select("article.list-item.row");

            for (int i = 0; i < listItemRow.size(); i++) {

                Elements title = listItemRow.get(i).select("h3");
                Elements shortDescribe = listItemRow.get(i).select("p.mt10");
                Elements tvDate = listItemRow.get(i).select("span.list-item-subtitle");
                Elements picture = listItemRow.get(i).select("img");
                Elements feedUrl = listItemRow.get(i).select("a[href]");
                Elements countOfVisit = listItemRow.get(i).select("div.span10.list-item-body");

                String[] counts = countOfVisit.text().split(" ");
                String visitCount = counts[1];
                String commentsCount = counts[0];
                String feedUrlString = feedUrl.attr("href");
                String pictureBig = picture.attr("src");

                if (pictureBig.contains("min"))
                    pictureBig = pictureBig.replace("_min", "");
                arNews.add(new News(title.text(), shortDescribe.text(), tvDate.text(),
                        pictureBig, feedUrlString, visitCount, commentsCount, false, Feed.NORMAL_TYPE));

            }
        }

        callback.onSuccess(arNews);
    }


}


