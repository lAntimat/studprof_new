package ru.lantimat.studprof.PhotoGallery;

/**
 * Created by GabdrakhmanovII on 04.09.2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import ru.lantimat.studprof.FullFeeds.FullFeedsActivity;
import ru.lantimat.studprof.ItemClickSupport;
import ru.lantimat.studprof.Photo.Photo;
import ru.lantimat.studprof.Photo.PhotoPresenter;
import ru.lantimat.studprof.Photo.PhotoRecyclerAdapter;
import ru.lantimat.studprof.Photo.PhotoView;
import ru.lantimat.studprof.R;


public class PhotoGalleryFragment extends Fragment implements PhotoGalleryView {

    private final String ARG_PARAM1 = "param1";

    RecyclerView recyclerView;
    PhotoGalleryRecyclerAdapter adapter;
    ArrayList<PhotoGalleryItem> ar;
    TextView textView;
    ImageView imageView;
    ProgressBar progressBar;
    PhotoGalleryPresenter presenter;
    String url;


    public PhotoGalleryFragment() {
        // Required empty public constructor
    }

    public PhotoGalleryFragment newInstance() {

        PhotoGalleryFragment fragment = new PhotoGalleryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, 1);
        fragment.setArguments(args);

        return fragment;
    }

    FullSizeImageView fullSizeImageView;

    public interface FullSizeImageView {
        void onAdd(ArrayList<PhotoGalleryItem> ar);
    }

    public void registerView(FullSizeImageView view) {
        fullSizeImageView = view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            getArguments().getInt(ARG_PARAM1);
        }

        ar = new ArrayList<>();
        adapter = new PhotoGalleryRecyclerAdapter(getContext(), ar);

        presenter = new PhotoGalleryPresenter(this);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, OrientationHelper.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(1024);
        recyclerView.setAdapter(adapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();

                int firstVisible = layoutManager.findFirstVisibleItemPosition();
                int visibleCount = Math.abs(firstVisible - layoutManager.findLastVisibleItemPosition());
                int itemCount = recyclerView.getAdapter().getItemCount();

                if ((firstVisible + visibleCount + 6) >= itemCount) {
                    presenter.loadMore();
                }
            }
        });

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                if (getFragmentManager().getBackStackEntryCount() == 0) openFullSizePhoto();
                if (fullSizeImageView != null) fullSizeImageView.onAdd(ar);

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_photo_gallery, null);

        textView = v.findViewById(R.id.textView);
        imageView = v.findViewById(R.id.imageView);
        progressBar = v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        recyclerView = v.findViewById(R.id.recyclerView);
        initRecyclerView();

        url = getActivity().getIntent().getStringExtra("url");
        presenter.loadDate(url);

        return v;
    }


    private void emptyPic() {
        if (ar.size() == 0) {
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
        ((PhotoGalleryActivity) getActivity()).progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showPhotos(ArrayList<PhotoGalleryItem> arPhoto) {
        //ar.clear();
        //ar.addAll(arPhoto);
        for (int i = ar.size(); i < arPhoto.size(); i++) {
            ar.add(arPhoto.get(i));
            adapter.notifyItemInserted(i);
            if (fullSizeImageView != null) fullSizeImageView.onAdd(ar);
        }
    }

    @Override
    public void openFullSizePhoto() {
        FragmentFullscreenImage fragmentFullscreenImage = new FragmentFullscreenImage();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .add(R.id.content_frame, fragmentFullscreenImage)
                .addToBackStack("list")
                .commit();
    }

    @Override
    public void setLoadedCount(int loadedCount, int size) {
        ((PhotoGalleryActivity) getActivity()).toolbar.setSubtitle(loadedCount + "/" + size);
    }

    @Override
    public void showToolbarLoading() {
        ((PhotoGalleryActivity) getActivity()).progressBar.setVisibility(View.VISIBLE);

    }
}
