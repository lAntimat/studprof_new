package ru.lantimat.studprof.Photo;

import java.util.ArrayList;

import ru.lantimat.studprof.Feeds.Feed;
import ru.lantimat.studprof.LoadingView;

/**
 * Created by lAntimat on 24.11.2017.
 */

public interface PhotoView extends LoadingView {

    void showPhotos(ArrayList<Photo> ar);

    void startPhotoGalleryActivity(String url, String img);

}
