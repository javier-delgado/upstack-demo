package com.javierdelgado.upstack_demo.adapters.base;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase añade funcionalidades de filtrado a un adapter
 */
/**
 * Esta clase añade funcionalidades de filtrado a un adapter
 */
public abstract class SortableAdapter<VH extends RecyclerView.ViewHolder, M> extends RecyclerView.Adapter<VH> implements Filterable {
    private final Class<M> modelClass;
    private MyFilter filter;
    private List<M> items;

    public SortableAdapter(List<M> items, Class<M> modelClass) {
        this.items = items;
        this.modelClass =  modelClass;
        initSortedList();
        sortedList.addAll(items);
    }

    protected abstract boolean filterItem(M item, CharSequence constraint);
    protected abstract int compareItems(M a, M b);
    protected abstract boolean areItemsTheSame(M item1, M item2);

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public M getItem(int position) {
        return sortedList.get(position);
    }

    public List<M> getItems() {
        return items;
    }

    //<editor-fold desc="filter and sorting">
    private class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<M> tempList = new ArrayList<M>();

                // search content in friend list
                for (M item : items) {                    ;
                    if (filterItem(item, constraint)) {
                        tempList.add(item);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = items.size();
                filterResults.values = items;
            }

            return filterResults;
        }
        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            replaceAll((List<M>) results.values);
        }
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new MyFilter();
        }
        return filter;
    }

    private SortedList<M> sortedList;
    private void initSortedList() {
        sortedList = new SortedList<>(modelClass, new SortedList.Callback<M>() {
            @Override
            public int compare(M a, M b) {
                return compareItems(a, b);
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(M oldItem, M newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(M item1, M item2) {
                return SortableAdapter.this.areItemsTheSame(item1, item2);
            }
        });
    }
    //</editor-fold>

    //<editor-fold desc="Gestion de items">
    protected void add(M newItem) {
        sortedList.add(newItem);
    }

    protected void remove(M newItem) {
        sortedList.remove(newItem);
    }

    protected void addAll(List<M> newItems) {
        sortedList.addAll(newItems);
    }

    protected void remove(List<M> newItems) {
        sortedList.beginBatchedUpdates();
        for (M newItem : newItems) {
            sortedList.remove(newItem);
        }
        sortedList.endBatchedUpdates();
    }

    protected void replaceAll(List<M> newItems) {
        sortedList.beginBatchedUpdates();
        for (int i = sortedList.size() - 1; i >= 0; i--) {
            final M newItem = sortedList.get(i);
            if (!newItems.contains(newItem)) {
                sortedList.remove(newItem);
            }
        }
        items.addAll(newItems);
        sortedList.addAll(newItems);
        sortedList.endBatchedUpdates();
    }

    protected void removeAll() {
        sortedList.beginBatchedUpdates();
        sortedList.clear();
        sortedList.endBatchedUpdates();
    };

    //Metodos publicos
    public void setItems(List<M> items) {
        removeAll();
        this.items = items;
        addAll(items);
    }

    public void removeAllItems() {
        this.items.clear();
        removeAll();
    }
    //</editor-fold>
}
