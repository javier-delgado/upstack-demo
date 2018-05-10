package com.javierdelgado.upstack_demo.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.javierdelgado.upstack_demo.R;
import com.javierdelgado.upstack_demo.adapters.SimpleAdapter;
import com.javierdelgado.upstack_demo.customComponents.EnhancedRecyclerView;
import com.javierdelgado.upstack_demo.models.Album;
import com.javierdelgado.upstack_demo.network.fetchers.AlbumsFetcher;
import com.javierdelgado.upstack_demo.utils.ItemClickSupport;
import com.javierdelgado.upstack_demo.vistas.base.BaseActivity;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @BindView(R.id.recAlbums) EnhancedRecyclerView recAlbums;
    @BindView(R.id.emptyView) View emptyView;
    @BindView(R.id.reloadView) View reloadView;
    private SimpleAdapter<Album> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupRecycler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchAlbums();
    }

    private void setupRecycler() {
        adapter = new SimpleAdapter<>(Album.class);
        recAlbums.setAdapter(adapter);
        recAlbums.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ItemClickSupport.addTo(recAlbums).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(MainActivity.this, PhotosActivity.class);
                intent.putExtra("album_id", adapter.getItem(position).getId());
                intent.putExtra("album_title", adapter.getItem(position).getTitle());
                startActivity(intent);
            }
        });
    }

    private void fetchAlbums() {
        showActivityOverlay();
        getBus().post(new AlbumsFetcher.FetchEvent());
    }

    @Subscribe
    public void onAlbumsFetched(AlbumsFetcher.SuccessEvent event) {
        recAlbums.setErrorOcurred(false);
        loadAlbums(event.albums);
        hideActivityOverlay();
    }

    @Subscribe
    public void onAlbumsFetchError(AlbumsFetcher.FailEvent event) {
        adapter.removeAllItems();
        recAlbums.setErrorOcurred(true);
        hideActivityOverlay();

    }

    private void loadAlbums(List<Album> albums) {
        adapter.setItems(albums);
        recAlbums.setEmptyView(emptyView);
        recAlbums.setErrorView(reloadView);
    }
}
