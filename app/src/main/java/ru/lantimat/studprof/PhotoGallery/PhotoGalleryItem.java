package ru.lantimat.studprof.PhotoGallery;

/**
 * Created by Ильназ on 06.11.2015.
 */
public class PhotoGalleryItem {

        public String photoUrl;
        public String photoBigSizeUrl;

        public PhotoGalleryItem(String _photoUrl, String _photoBigSizeUrl) {
            photoUrl = _photoUrl;
            photoBigSizeUrl = _photoBigSizeUrl;
        }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoBigSizeUrl() {
        return photoBigSizeUrl;
    }

    public void setPhotoBigSizeUrl(String photoBigSizeUrl) {
        this.photoBigSizeUrl = photoBigSizeUrl;
    }
}
