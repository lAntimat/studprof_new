package ru.lantimat.studprof.Feeds;

import java.util.ArrayList;

import ru.lantimat.studprof.LoadingView;

/**
 * Created by lAntimat on 24.11.2017.
 */

public interface FeedView extends LoadingView {

    void showFeeds(ArrayList<Feed> ar);

    void startFeedActivity(String url, String img);

}
