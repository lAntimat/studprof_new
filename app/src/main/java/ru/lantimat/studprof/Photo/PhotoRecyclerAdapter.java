package ru.lantimat.studprof.Photo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.lantimat.studprof.Feeds.Feed;
import ru.lantimat.studprof.R;


/**
 * Created by GabdrakhmanovII on 31.07.2017.
 */

public class PhotoRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Photo> mList;

    public PhotoRecyclerAdapter(Context context, ArrayList<Photo> itemList) {
        this.context = context;
        this.mList = itemList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_photo, parent, false);
        return new NormalFeedViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String date = mList.get(position).getData();
        String title = mList.get(position).getTitle();
        String subTitle = mList.get(position).getSubTitle();
        ArrayList<String> images = mList.get(position).getImgUrls();
        String url = mList.get(position).getAlbumUrl();
        String countOfVisit = mList.get(position).getCountOfVisit();
        String countOfComments= mList.get(position).getCountOfComment();
        String countOfPhotos = mList.get(position).getCountOfPhotos();

        ArrayList<ImageView> arImageView = new ArrayList<>();
        arImageView.add(((NormalFeedViewHolder) holder).imageView1);
        arImageView.add(((NormalFeedViewHolder) holder).imageView2);
        arImageView.add(((NormalFeedViewHolder) holder).imageView3);
        arImageView.add(((NormalFeedViewHolder) holder).imageView4);
        arImageView.add(((NormalFeedViewHolder) holder).imageView5);
        arImageView.add(((NormalFeedViewHolder) holder).imageView6);
        arImageView.add(((NormalFeedViewHolder) holder).imageView7);
        arImageView.add(((NormalFeedViewHolder) holder).imageView8);


        ((NormalFeedViewHolder) holder).title.setText(title);
        ((NormalFeedViewHolder) holder).subTitle.setText(subTitle);
        ((NormalFeedViewHolder) holder).date.setText(date);
        ((NormalFeedViewHolder) holder).visitCount.setText(countOfVisit);
        ((NormalFeedViewHolder) holder).photosCount.setText(countOfPhotos);
        ((NormalFeedViewHolder) holder).commentCount.setText(countOfComments);

        for (int i = 0; i < 8 ; i++) {
            Picasso.with(context).load(images.get(i)).fit().placeholder(R.drawable.studprofoper).into(arImageView.get(i));
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

        return 0;
    }

    public static class NormalFeedViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView subTitle;
        private TextView date;
        private TextView visitCount;
        private TextView photosCount;
        private TextView commentCount;
        private ImageView imageView1;
        private ImageView imageView2;
        private ImageView imageView3;
        private ImageView imageView4;
        private ImageView imageView5;
        private ImageView imageView6;
        private ImageView imageView7;
        private ImageView imageView8;

        public NormalFeedViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tvFeedTitle);
            subTitle = (TextView) itemView.findViewById(R.id.tvFeedSupportingText);
            date = (TextView) itemView.findViewById(R.id.tvFeedtextView);
            visitCount = (TextView) itemView.findViewById(R.id.tvVisitCount);
            photosCount = (TextView) itemView.findViewById(R.id.tvPhotoCount);
            commentCount = (TextView) itemView.findViewById(R.id.tvCommentCount);
            imageView1 = (ImageView) itemView.findViewById(R.id.imageView1);
            imageView2 = (ImageView) itemView.findViewById(R.id.imageView2);
            imageView3 = (ImageView) itemView.findViewById(R.id.imageView3);
            imageView4 = (ImageView) itemView.findViewById(R.id.imageView4);
            imageView5 = (ImageView) itemView.findViewById(R.id.imageView5);
            imageView6 = (ImageView) itemView.findViewById(R.id.imageView6);
            imageView7 = (ImageView) itemView.findViewById(R.id.imageView7);
            imageView8 = (ImageView) itemView.findViewById(R.id.imageView8);
        }
    }
}
