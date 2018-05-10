package com.javierdelgado.upstack_demo.customComponents;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by javier on 5/8/17.
 */

public class EnhancedRecyclerView extends RecyclerView {
    private boolean errorOcurred = false;
    private View emptyView;
    private View errorView;

    final private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };

    public EnhancedRecyclerView(Context context) {
        super(context);
    }

    public EnhancedRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EnhancedRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    void checkIfEmpty() {
        if (getAdapter() != null) {
            final boolean adapterEmpty = getAdapter().getItemCount() == 0;

            boolean emptyViewVisibility = adapterEmpty && !errorOcurred;
            boolean errorViewVisibility = adapterEmpty && errorOcurred;
            boolean listVisibility = !adapterEmpty;

            if (emptyView != null) emptyView.setVisibility(emptyViewVisibility ? View.VISIBLE : View.GONE);
            if (errorView != null) errorView.setVisibility(errorViewVisibility ? View.VISIBLE : View.GONE);
            setVisibility(listVisibility? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }

        checkIfEmpty();
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        checkIfEmpty();
    }

    public void setErrorView(View errorView) {
        this.errorView = errorView;
        checkIfEmpty();
    }

    public void setErrorOcurred(boolean errorOcurred) {
        this.errorOcurred = errorOcurred;
    }
}
