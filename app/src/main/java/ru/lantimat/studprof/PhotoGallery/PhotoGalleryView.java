package ru.lantimat.studprof.PhotoGallery;

import java.util.ArrayList;

import ru.lantimat.studprof.LoadingView;
import ru.lantimat.studprof.Photo.Photo;

/**
 * Created by lAntimat on 24.11.2017.
 */

public interface PhotoGalleryView extends LoadingView {

    void showPhotos(ArrayList<PhotoGalleryItem> arPhotos);

    void openFullSizePhoto(String url, String img);

    void setLoadedCount(int loadedCount, int size);

    void showToolbarLoading();
}
