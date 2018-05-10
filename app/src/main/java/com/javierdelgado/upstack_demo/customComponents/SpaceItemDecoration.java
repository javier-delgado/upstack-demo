package com.javierdelgado.upstack_demo.customComponents;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.javierdelgado.upstack_demo.utils.MeasureUtils;


/**
 * Created by javier on 2/10/17.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int columnCount;
    private final boolean includeEdges;
    private int space;

    public SpaceItemDecoration(Context context, int spaceDip, int spanCount, boolean includeEdges) {
        this.space = MeasureUtils.dipToPixel(spaceDip, context);
        this.columnCount = spanCount;
        this.includeEdges = includeEdges;
    }

    public SpaceItemDecoration(int spacePx, int spanCount, boolean includeEdges) {
        this.space = spacePx;
        this.columnCount = spanCount;
        this.includeEdges = includeEdges;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position

        if (position >= 0) {
            int column = position % columnCount; // item column

            if (includeEdges) {
                outRect.left = space - column * space / columnCount; // space - column * ((1f / columnCount) * space)
                outRect.right = (column + 1) * space / columnCount; // (column + 1) * ((1f / columnCount) * space)

                if (position < columnCount) { // top edge
                    outRect.top = space;
                }
                outRect.bottom = space; // item bottom
            } else {
                outRect.left = column * space / columnCount; // column * ((1f / columnCount) * space)
                outRect.right = space - (column + 1) * space / columnCount; // space - (column + 1) * ((1f /    columnCount) * space)
                if (position >= columnCount) {
                    outRect.top = space; // item top
                }
            }
        } else {
            outRect.left = 0;
            outRect.right = 0;
            outRect.top = 0;
            outRect.bottom = 0;
        }
    }
}