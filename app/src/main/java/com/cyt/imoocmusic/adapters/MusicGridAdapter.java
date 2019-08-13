package com.cyt.imoocmusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.cyt.imoocmusic.R;
import com.cyt.imoocmusic.activities.AlbumListActivity;

public class MusicGridAdapter extends RecyclerView.Adapter<MusicGridAdapter.ViewHolder> {

    private Context mContext;


    public MusicGridAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_grid_music, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Glide.with(mContext)
                .load("http://res.lgdsunday.club/poster-1.png")
                .into(holder.ivIcon);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AlbumListActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ivIcon = itemView.findViewById(R.id.iv_icon);
        }
    }
}
