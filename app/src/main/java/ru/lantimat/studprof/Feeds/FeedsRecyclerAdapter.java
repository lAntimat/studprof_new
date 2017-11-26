package ru.lantimat.studprof.Feeds;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ru.lantimat.studprof.R;


/**
 * Created by GabdrakhmanovII on 31.07.2017.
 */

public class FeedsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Feed> mList;

    public FeedsRecyclerAdapter(ArrayList<Feed> itemList) {
        this.mList = itemList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Feed.FIXED_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_fixed_feed, parent, false);
                return new NormalFeedViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_normal_feed, parent, false);
                return new FixedFeedViewHolder(view);
        }

        /*view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_mark, parent, false);
        return new MarksRecyclerAdapter.FixedFeedViewHolder(view);*/

    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String time = mList.get(position).getDate();
        String name = mList.get(position).getTitle();
        String place = mList.get(position).getSubTitle();

            switch (getItemViewType(position)) {
                case Feed.FIXED_TYPE:

                    ((NormalFeedViewHolder) holder).mDesc.setText(place);
                    break;
                case Feed.NORMAL_TYPE:
                    ((FixedFeedViewHolder) holder).mTime.setText(time);
                    ((FixedFeedViewHolder) holder).mName.setText(name);
                    ((FixedFeedViewHolder) holder).mPlace.setText(place);
                    break;
            }


    }
    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (mList != null) {
            return mList.get(position).getViewType();
        }
        return 0;
    }




    public static class FixedFeedViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mTime;
        private TextView mPlace;
        public FixedFeedViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.tvName);
            mTime = (TextView) itemView.findViewById(R.id.tvDesc);
            mPlace = (TextView) itemView.findViewById(R.id.tvDesc);
        }
    }
    public static class NormalFeedViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mDesc;
        private ImageView mImg;
        public NormalFeedViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.tvName);
            mDesc = (TextView) itemView.findViewById(R.id.tvDesc);
            mImg = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
