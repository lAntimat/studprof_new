package ru.lantimat.studprof.PhotoGallery;

/**
 * Created by GabdrakhmanovII on 04.09.2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import ru.lantimat.studprof.R;
import ru.lantimat.studprof.Utils.WrapContentViewPager;


public class FragmentFullscreenImage extends Fragment implements PhotoGalleryFragment.FullSizeImageView{

    private final String ARG_PARAM1 = "param1";

    RecyclerView recyclerView;
    FullscreenImageAdapter adapter;
    ArrayList<PhotoGalleryItem> ar;
    TextView textView;
    ImageView imageView;
    ProgressBar progressBar;
    PhotoGalleryPresenter presenter;
    String url;
    WrapContentViewPager viewPager;

    public FragmentFullscreenImage() {
        // Required empty public constructor
    }

    public FragmentFullscreenImage newInstance() {

        FragmentFullscreenImage fragment = new FragmentFullscreenImage();
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

        ((PhotoGalleryActivity) getActivity()).photoGalleryFragment.registerView(this);
        ar = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_photo_full_size, null);

        textView = v.findViewById(R.id.textView);
        imageView = v.findViewById(R.id.imageView);
        progressBar = v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        adapter = new FullscreenImageAdapter(getContext(), ar);
        viewPager = v.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        url = getActivity().getIntent().getStringExtra("url");

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
    public void onAdd(ArrayList<PhotoGalleryItem> ar) {
        this.ar.clear();
        this.ar.addAll(ar);
        adapter.notifyDataSetChanged();
    }
}
