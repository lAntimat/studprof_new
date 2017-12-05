package ru.lantimat.studprof.Feeds;

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

import ru.lantimat.studprof.FullFeeds.FullFeedsActivity;
import ru.lantimat.studprof.FullNews.FullNewsActivity;
import ru.lantimat.studprof.ItemClickSupport;
import ru.lantimat.studprof.R;


public class FeedFragment extends Fragment implements FeedView{

    private final String ARG_PARAM1 = "param1";

    RecyclerView recyclerView;
    FeedsRecyclerAdapter adapter;
    ArrayList<Feed> arFeeds;
    TextView textView;
    ImageView imageView;
    ProgressBar progressBar;
    FeedPresenter presenter;



    public FeedFragment() {
        // Required empty public constructor
    }

    public FeedFragment newInstance() {

        FeedFragment fragment = new FeedFragment();
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

        arFeeds = new ArrayList<>();
        adapter = new FeedsRecyclerAdapter(getContext(), arFeeds);

        presenter = new FeedPresenter(this);
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
                startFeedActivity(arFeeds.get(position).getUrl(), arFeeds.get(position).getImage());
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

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        initRecyclerView();

        presenter.loadDate();

        return v;
    }


    private void emptyPic() {
        if (arFeeds.size() == 0) {
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
    public void showFeeds(ArrayList<Feed> ar) {
        arFeeds.clear();
        arFeeds.addAll(ar);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void startFeedActivity(String url, String img) {
        Intent intent = new Intent(getContext(), FullFeedsActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("img", img);
        startActivity(intent);
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
}
