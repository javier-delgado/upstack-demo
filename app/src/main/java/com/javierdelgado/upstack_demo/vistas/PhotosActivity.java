package com.javierdelgado.upstack_demo.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.javierdelgado.upstack_demo.R;
import com.javierdelgado.upstack_demo.adapters.PhotosAdapter;
import com.javierdelgado.upstack_demo.customComponents.EnhancedRecyclerView;
import com.javierdelgado.upstack_demo.customComponents.SpaceItemDecoration;
import com.javierdelgado.upstack_demo.models.Photo;
import com.javierdelgado.upstack_demo.network.fetchers.PhotosFetcher;
import com.javierdelgado.upstack_demo.utils.ItemClickSupport;
import com.javierdelgado.upstack_demo.vistas.base.BaseActivity;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotosActivity extends BaseActivity {
    @BindView(R.id.recPhotos) EnhancedRecyclerView recPhotos;
    @BindView(R.id.emptyView) View emptyView;
    @BindView(R.id.reloadView) View reloadView;
    PhotosAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        ButterKnife.bind(this);
        setTitle(getIntent().getStringExtra("album_title"));
        showUpButton(true);
        setupRecycler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchPhotos();
    }

    private void setupRecycler() {
        adapter = new PhotosAdapter();
        recPhotos.setAdapter(adapter);
        recPhotos.setLayoutManager(new GridLayoutManager(this, 2));
        recPhotos.addItemDecoration(new SpaceItemDecoration(this, 4, 2, true));
        ItemClickSupport.addTo(recPhotos).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(PhotosActivity.this, FullScreenImageActivity.class);
                intent.putExtra("img_url", adapter.getItem(position).getUrl());
                startActivity(intent);
            }
        });
    }

    private void fetchPhotos() {
        showActivityOverlay();
        getBus().post(new PhotosFetcher.FetchEvent(getIntent().getIntExtra("album_id", 0)));
    }

    @Subscribe
    public void onPhotosFetched(PhotosFetcher.SuccessEvent event) {
        recPhotos.setErrorOcurred(false);
        loadPhotos(event.photos);
        hideActivityOverlay();
    }

    @Subscribe
    public void onPhotosFetchError(PhotosFetcher.FailEvent event) {
        adapter.removeAllItems();
        recPhotos.setErrorOcurred(true);
        hideActivityOverlay();

    }

    private void loadPhotos(List<Photo> photos) {
        adapter.setItems(photos);
        recPhotos.setEmptyView(emptyView);
        recPhotos.setErrorView(reloadView);
    }
}
