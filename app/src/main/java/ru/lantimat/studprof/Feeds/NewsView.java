package ru.lantimat.studprof.Feeds;

import java.util.ArrayList;

import ru.lantimat.studprof.LoadingView;

/**
 * Created by lAntimat on 24.11.2017.
 */

public interface NewsView extends LoadingView {

    void showNews(ArrayList<News> ar);

    void startNewsActivity(String url);

}
