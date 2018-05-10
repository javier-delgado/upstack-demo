package com.javierdelgado.upstack_demo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.javierdelgado.upstack_demo.R;
import com.javierdelgado.upstack_demo.adapters.base.SimpleListableItem;
import com.javierdelgado.upstack_demo.adapters.base.SortableAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javier on 4/24/17.
 */

public class SimpleAdapter<T extends SimpleListableItem> extends SortableAdapter<SimpleAdapter.ViewHolder, T> {

    public SimpleAdapter(List<T> items, Class<T> clazz) {
        super(items, clazz);
    }

    public SimpleAdapter(Class<T> clazz) {
        super(new ArrayList<T>(), clazz);
    }

    @Override
    public SimpleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_recycler_item, parent, false);
        return new SimpleAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SimpleAdapter.ViewHolder holder, int position) {
        final SimpleListableItem item = getItem(position);

        holder.txtItem.setText(item.getText());
        if (item.enabled()) {
            holder.container.setEnabled(true);
            holder.txtItem.setEnabled(true);
            holder.arrow.setVisibility(View.VISIBLE);
        } else {
            holder.container.setEnabled(false);
            holder.txtItem.setEnabled(false);
            holder.arrow.setVisibility(View.GONE);
        }
    }

    //<editor-fold desc="Filtrado y ordenamiento">
    @Override
    protected boolean filterItem(T item, CharSequence constraint) {
        return false;
    }

    @Override
    protected int compareItems(T a, T b) {
        return a.compareTo(b);
    }

    @Override
    protected boolean areItemsTheSame(T item1, T item2) {
        return item1.equals(item2);
    }
    //</editor-fold>

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtItem) TextView txtItem;
        @BindView(R.id.container) ViewGroup container;
        @BindView(R.id.arrow) ImageView arrow;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
