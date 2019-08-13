package com.cyt.imoocmusic.views;

import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;

    public GridSpaceItemDecoration(int space, RecyclerView parent){
        mSpace = space;
        getRecyclerViewOffsets(parent);
    }

    /**
     * @param outRect Item的矩形边界
     * @param view ItemView
     * @param parent RecyclerView
     * @param state RecyclerView的状态
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.left = mSpace;


    }

    private void getRecyclerViewOffsets(RecyclerView parent){
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) parent.getLayoutParams();
        layoutParams.leftMargin = -mSpace;
        parent.setLayoutParams(layoutParams);
    }
}
