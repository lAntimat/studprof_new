package ru.lantimat.studprof.PhotoGallery;

import java.util.ArrayList;

import ru.lantimat.studprof.Photo.Photo;
import ru.lantimat.studprof.Photo.PhotoView;
import ru.lantimat.studprof.Repository;
import ru.lantimat.studprof.Utils.Constants;

/**
 * Created by lAntimat on 24.11.2017.
 */

public class PhotoGalleryPresenter {

    private PhotoGalleryView view;
    private Repository repository;
    private int page = 1;
    private boolean isLoading = false;
    private boolean isNoMore = false;
    private String loadMoreUrl = Constants.urlStudProf;

    public PhotoGalleryPresenter(PhotoGalleryView view) {
        this.view = view;
    }

    public void loadDate(String url) {
        if(!isLoading) {
            view.showLoading();
            isLoading = true;
            Repository.webViewGetImages(url, new Repository.PhotoGalleryCallback() {

                @Override
                public void onSuccess(ArrayList<PhotoGalleryItem> ar) {
                    view.hideLoading();
                    view.showPhotos(ar);
                    page++;
                    isLoading = false;
                }

                @Override
                public void loadCount(int loaded, int size) {
                    if(loaded >= size) isNoMore = true;
                    else isNoMore = false;
                    view.setLoadedCount(loaded, size);
                }

                @Override
                public void onFailure(Throwable error) {
                    isLoading = false;
                    view.hideLoading();
                    view.showError("Ошибка загрузки данных" + error.getLocalizedMessage());
                }
            });
        }
    }

    public void loadMore() {
        if(!isLoading & !isNoMore) {
            view.showToolbarLoading();
            Repository.webViewGetMore();
        }
    }
}
