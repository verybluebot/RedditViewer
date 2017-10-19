package com.shulg.redditshow.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shulg.redditshow.R;
import com.shulg.redditshow.modules.SubReddit;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shulg on 10/19/17.
 */

public class MainPageAdapter extends RecyclerView.Adapter<MainPageAdapter.ViewHolder> {
    public Context context;
    public List<SubReddit> subReddits;

    public MainPageAdapter(Context ctx, List<SubReddit> subReddits) {
        this.context = ctx;
        this.subReddits = subReddits;


    }

    @Override
    public MainPageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_row, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(MainPageAdapter.ViewHolder holder, int position) {

        SubReddit subReddit = subReddits.get(position);

        holder.headerText.setText(subReddit.searchName);

        String imageUrl = subReddit.iconImage.length() > 5 ? subReddit.iconImage : subReddit.headerImage;

        if (subReddit.iconImage != null && imageUrl.length() > 0) {
            Picasso.with(context)
                    .load(imageUrl)
                    .fit()
                    .centerInside()
                    .into(holder.avatarImage);
        }
    }

    @Override
    public int getItemCount() {
        return subReddits == null ? 0 : subReddits.size();
    }

    public void setSubReddits(List<SubReddit> r) {
        subReddits = r;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView headerText;
        public ImageView avatarImage;

        public ViewHolder(View itemView, Context ctx) {
            super(itemView);

            context = ctx;

            headerText = (TextView) itemView.findViewById(R.id.mainRowText);
            avatarImage = (ImageView) itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // go to reddit page
                    Log.i("CLICK", "row was clicked");
                }
            });
        }
    }
}
