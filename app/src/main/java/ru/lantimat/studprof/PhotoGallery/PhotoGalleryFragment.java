package ru.lantimat.studprof.PhotoGallery;

/**
 * Created by GabdrakhmanovII on 04.09.2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class PhotoGalleryFragment extends Fragment implements PhotoGalleryView{

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
        recyclerView.setAdapter(adapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int firstVisible = layoutManager.findFirstVisibleItemPosition();
                int visibleCount = Math.abs(firstVisible - layoutManager.findLastVisibleItemPosition());
                int itemCount = recyclerView.getAdapter().getItemCount();

                if ((firstVisible + visibleCount + 1) >= itemCount) {
                    presenter.loadMore();
                }
            }
        });

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
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
        ar.clear();
        ar.addAll(arPhoto);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void openFullSizePhoto(String url, String img) {
        Intent intent = new Intent(getContext(), FullFeedsActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("img", img);
        startActivity(intent);
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
