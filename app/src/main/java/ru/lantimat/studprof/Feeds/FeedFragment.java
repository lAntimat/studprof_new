package ru.lantimat.studprof.Feeds;

/**
 * Created by GabdrakhmanovII on 04.09.2017.
 */

import android.os.AsyncTask;
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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import ru.lantimat.studprof.R;


public class FeedFragment extends Fragment implements FeedView{

    private final String ARG_PARAM1 = "param1";

    RecyclerView recyclerView;
    FeedsRecyclerAdapter scheduleRecyclerAdapter;
    ArrayList<Feed> arMainFeeds;
    TextView textView;
    ImageView imageView;
    ProgressBar progressBar;




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

        arMainFeeds = new ArrayList<>();
        scheduleRecyclerAdapter = new FeedsRecyclerAdapter(arMainFeeds);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
        recyclerView.setAdapter(scheduleRecyclerAdapter);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_feeds, null);

        textView = v.findViewById(R.id.textView);
        imageView = v.findViewById(R.id.imageView);
        progressBar = v.findViewById(R.id.progressBar);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        initRecyclerView();
        return v;
    }

    private void parseScheduleFromString(String str) {

        if(arMainFeeds.size() == 0) emptyPic();

    }

    private void onPreExecuteMethod() {
        if(arMainFeeds.isEmpty()) progressBar.setVisibility(View.VISIBLE);
    }

    private void onPostExecuteMethod() {
        progressBar.setVisibility(View.INVISIBLE);
        scheduleRecyclerAdapter.notifyDataSetChanged();
        emptyPic();
    }

    private void emptyPic() {
        if (arMainFeeds.size() == 0) {
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStop() {
        //if(loadScheduleFromCash!=null) loadScheduleFromCash.cancel(true);
        //if(parseSchedule!=null) parseSchedule.cancel(true);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void loadDate() {

    }

    public class ParseSchedule extends AsyncTask<byte[], Void, Void> {

        @Override
        protected void onPreExecute() {
            onPreExecuteMethod();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(byte[]... params) {

            String str = null;
            try {

                //str = new String(params[0], "UTF-8");
                str = new String(params[0], "windows-1251");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            //feedsRecyclerAdapter.notifyDataSetChanged();
            //progressBar.setVisibility(View.INVISIBLE);
            onPostExecuteMethod();
            super.onPostExecute(aVoid);
        }
    }

    public class LoadScheduleFromCash extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            onPreExecuteMethod();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {

            //Log.d("MainActivity", "ParseFeed");

            String str = null;
            str = String.valueOf(params[0]);
            parseScheduleFromString(str);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //feedsRecyclerAdapter.notifyDataSetChanged();
            //progressBar.setVisibility(View.INVISIBLE);
            onPostExecuteMethod();
            super.onPostExecute(aVoid);
        }
    }

}
