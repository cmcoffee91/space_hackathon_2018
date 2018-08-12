package com.yellowspace.coffee.yellowspace;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {

    private List<Feed> feedList;
    String key;
    Activity activity;
    private int REQ_PLAYER_CODE  = 1;
    int cornerRadius;
    int cardColor;
    int textColor;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView imageView;



        public MyViewHolder(View view) {
            super(view);


            imageView = (ImageView)view.findViewById(R.id.nasaImage) ;


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Feed video = feedList.get(getAdapterPosition());

                   /* Intent videoIntent = YouTubeStandalonePlayer.createVideoIntent(activity, key, video.getVideoID(), 0, true, false);

                    activity.startActivityForResult(videoIntent, REQ_PLAYER_CODE);
                    */


                }
            });


            


            name = (TextView) view.findViewById(R.id.description);
           // name.setTextColor(textColor);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });



        }
    }




    public FeedAdapter(List<Feed> feedList, Activity activity) {
        this.activity  = activity;

        this.feedList = feedList;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_frag_layout, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Feed video = feedList.get(position);
        holder.name.setText(video.getDescription());


        Glide.with(activity)
                .load(video.getImageUrl() ) // Uri of the picture
                .into(holder.imageView);




    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }
}
