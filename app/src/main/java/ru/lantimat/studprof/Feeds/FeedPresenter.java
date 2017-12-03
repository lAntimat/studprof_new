package ru.lantimat.studprof.Feeds;

import java.util.ArrayList;

import ru.lantimat.studprof.Repository;

/**
 * Created by lAntimat on 24.11.2017.
 */

public class FeedPresenter {

    private FeedView view;
    private Repository repository;
    private int page = 1;
    private boolean isLoading = false;

    public FeedPresenter(FeedView view) {
        this.view = view;
    }

    public void loadDate() {
        if(!isLoading) {
            view.showLoading();
            isLoading = true;
            Repository.getFeeds(page, new Repository.FeedCallback() {
                @Override
                public void onSuccess(ArrayList<Feed> ar) {
                    view.hideLoading();
                    view.showFeeds(ar);
                    page++;
                    isLoading = false;
                }
            });
        }
    }
}
