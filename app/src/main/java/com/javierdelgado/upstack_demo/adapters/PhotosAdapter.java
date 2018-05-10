package com.javierdelgado.upstack_demo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.javierdelgado.upstack_demo.R;
import com.javierdelgado.upstack_demo.adapters.base.SortableAdapter;
import com.javierdelgado.upstack_demo.models.Photo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javier on 2/9/17.
 */

public class PhotosAdapter extends SortableAdapter<PhotosAdapter.ViewHolder, Photo> {

    public PhotosAdapter() {
        super(new ArrayList<Photo>(), Photo.class);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Photo photo = getItem(position);
        Context context = holder.itemView.getContext();
        Log.d("debug", photo.getThumbnailUrl());
        Glide.with(context)
                .load(photo.getThumbnailUrl())
                .apply(new RequestOptions().placeholder(R.drawable.photo_placeholder))
                .into(holder.imgPhoto);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgPhoto)
        ImageView imgPhoto;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }



    @Override
    protected boolean filterItem(Photo photo, CharSequence constraint) {
        return true;
    }

    @Override
    protected int compareItems(Photo a, Photo b) {
        return 0;
    }

    @Override
    protected boolean areItemsTheSame(Photo item1, Photo item2) {
        return item1.getId() == item2.getId();
    }
}
