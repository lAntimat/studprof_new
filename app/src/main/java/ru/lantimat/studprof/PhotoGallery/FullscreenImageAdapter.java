package ru.lantimat.studprof.PhotoGallery;

/**
 * Created by GabdrakhmanovII on 06.12.2017.
 */

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.lantimat.studprof.R;

public class FullscreenImageAdapter extends PagerAdapter {

    public static int fullScreenImagePosition;

    private Context context;
    private ArrayList<PhotoGalleryItem> ar;
    //private ImagesArrayList _imagesArrayList;
    private LayoutInflater inflater;
    private TextView _textView;
    private ProgressBar _progressBar;
    Context ctx;
    final String TAG = "FullScreenImageAdapter";

    // constructor
    public FullscreenImageAdapter(Context context, ArrayList<PhotoGalleryItem> ar) {
        this.context = context;
        this.ar = ar;

    }

    @Override
    public int getCount() {
        return this.ar.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView imgDisplay;
        Button btnClose;

        fullScreenImagePosition = position;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.viewpager_item_image, container,
                false);
        imgDisplay = viewLayout.findViewById(R.id.photo_view);

        btnClose = (Button) viewLayout.findViewById(R.id.btnClose);


        Uri uri = Uri.parse(ar.get(position).getPhotoBigSizeUrl());
        Picasso.with(context)
                .load(uri)
                .into(imgDisplay, new Callback() {
                    @Override
                    public void onSuccess() {
                        //_progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });




        // close button click event
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //_activity.finish();
            }
        });


        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}
