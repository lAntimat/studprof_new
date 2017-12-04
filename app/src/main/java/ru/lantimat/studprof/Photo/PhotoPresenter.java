package ru.lantimat.studprof.Photo;

import java.util.ArrayList;

import ru.lantimat.studprof.Feeds.Feed;
import ru.lantimat.studprof.Feeds.FeedView;
import ru.lantimat.studprof.Repository;

/**
 * Created by lAntimat on 24.11.2017.
 */

public class PhotoPresenter {

    private PhotoView view;
    private Repository repository;
    private int page = 1;
    private boolean isLoading = false;

    public PhotoPresenter(PhotoView view) {
        this.view = view;
    }

    public void loadDate() {
        if(!isLoading) {
            view.showLoading();
            isLoading = true;
            Repository.getPhotos(page, new Repository.PhotosCallback() {
                @Override
                public void onSuccess(ArrayList<Photo> ar) {
                    view.hideLoading();
                    view.showPhotos(ar);
                    page++;
                    isLoading = false;
                }
            });
        }
    }
}
