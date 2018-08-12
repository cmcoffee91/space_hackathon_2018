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

public class ARAdapter extends RecyclerView.Adapter<ARAdapter.MyViewHolder> {

    private List<String> feedList;
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



            name = (TextView) view.findViewById(R.id.arSubject);
            // name.setTextColor(textColor);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, AR_activity.class);
                    intent.putExtra("type",feedList.get(getAdapterPosition()));
                    activity.startActivity(intent);

                }
            });



        }
    }




    public ARAdapter(List<String> feedList, Activity activity) {
        this.activity  = activity;

        this.feedList = feedList;


    }

    @Override
    public ARAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ar_frag_layout, parent, false);


        return new ARAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ARAdapter.MyViewHolder holder, int position) {

        holder.name.setText(feedList.get(position));

    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }
}
