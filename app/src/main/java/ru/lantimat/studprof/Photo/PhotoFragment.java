package ru.lantimat.studprof.Photo;

/**
 * Created by GabdrakhmanovII on 04.09.2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import ru.lantimat.studprof.Feeds.Feed;
import ru.lantimat.studprof.Feeds.FeedPresenter;
import ru.lantimat.studprof.Feeds.FeedView;
import ru.lantimat.studprof.Feeds.FeedsRecyclerAdapter;
import ru.lantimat.studprof.FullFeeds.FullFeedsActivity;
import ru.lantimat.studprof.ItemClickSupport;
import ru.lantimat.studprof.R;


public class PhotoFragment extends Fragment implements PhotoView{

    private final String ARG_PARAM1 = "param1";

    RecyclerView recyclerView;
    PhotoRecyclerAdapter adapter;
    ArrayList<Photo> ar;
    TextView textView;
    ImageView imageView;
    ProgressBar progressBar;
    PhotoPresenter presenter;



    public PhotoFragment() {
        // Required empty public constructor
    }

    public PhotoFragment newInstance() {

        PhotoFragment fragment = new PhotoFragment();
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
        adapter = new PhotoRecyclerAdapter(getContext(), ar);

        presenter = new PhotoPresenter(this);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
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
                    presenter.loadDate();
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

        View v = inflater.inflate(R.layout.fragment_feeds, null);

        textView = v.findViewById(R.id.textView);
        imageView = v.findViewById(R.id.imageView);
        progressBar = v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        recyclerView = v.findViewById(R.id.recyclerView);
        initRecyclerView();

        presenter.loadDate();

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
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showPhotos(ArrayList<Photo> arPhoto) {
        ar.clear();
        ar.addAll(arPhoto);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void startPhotoGalleryActivity(String url, String img) {
        Intent intent = new Intent(getContext(), FullFeedsActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("img", img);
        startActivity(intent);
    }
}
