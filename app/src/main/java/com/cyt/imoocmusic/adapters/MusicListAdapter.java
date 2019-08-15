package com.cyt.imoocmusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cyt.imoocmusic.R;
import com.cyt.imoocmusic.activities.PlayMusicActivity;
import com.cyt.imoocmusic.models.MusicModel;

import java.util.List;


public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {

    private Context mContext;
    private View mItemView;
    private RecyclerView mRv;
    private boolean isCalcaulationRvHeight;

    private List<MusicModel> mDataSource;

    public MusicListAdapter(Context context,RecyclerView recyclerView,List<MusicModel> dataSource){
        mContext = context;
        mRv = recyclerView;
        this.mDataSource = dataSource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mItemView = LayoutInflater.from(mContext).inflate(R.layout.item_list_music,parent,false);
        return new ViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setRecyclerViewHeight();

        final MusicModel musicModel = mDataSource.get(position);

        Glide.with(mContext)
                .load(musicModel.getPoster())
                .into(holder.ivIcon);

        holder.mTvName.setText(musicModel.getName());
        holder.mTvAuthor.setText(musicModel.getAuthor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PlayMusicActivity.class);
                intent.putExtra(PlayMusicActivity.MUSIC_ID,musicModel.getMusicId());
                mContext.startActivity(intent);
    }
});
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivIcon;
        View itemView;
        TextView mTvName,mTvAuthor;

        public ViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            ivIcon = itemView.findViewById(R.id.iv_icon);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvAuthor = itemView.findViewById(R.id.tv_author);
        }
    }

    /**
     * 1、获取ItemView的高度
     * 2、获取ItemView的数量
     * 3、高度*数量
     */
    private void setRecyclerViewHeight(){

        if (isCalcaulationRvHeight || mRv == null)
            return;

        isCalcaulationRvHeight = true;

        // 1、获取ItemView的高度
        RecyclerView.LayoutParams itemViewLp = (RecyclerView.LayoutParams) mItemView.getLayoutParams();
        // 2、获取ItemView的数量
        int itemCount = getItemCount();
        // 3、高度*数量
        int recyclerViewHeight = itemViewLp.height * itemCount;
        // 设置RecyclerView高度
        LinearLayout.LayoutParams rvLp = (LinearLayout.LayoutParams) mRv.getLayoutParams();
        rvLp.height = recyclerViewHeight;
        mRv.setLayoutParams(rvLp);
    }
}
