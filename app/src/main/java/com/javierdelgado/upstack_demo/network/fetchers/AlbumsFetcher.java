package com.javierdelgado.upstack_demo.network.fetchers;

import org.greenrobot.eventbus.Subscribe;

import com.javierdelgado.upstack_demo.models.Album;
import com.javierdelgado.upstack_demo.network.NetworkExchanger;

import java.util.List;

import retrofit2.Response;

/**
 * Created by javier on 9/11/17.
 */

public class AlbumsFetcher extends NetworkExchanger<List<Album>> {

    @Subscribe
    public void fetch(FetchEvent event) {
        startHttpRequest(getApi().getAlbums());
    }

    @Override
    protected void onResponseSuccessful(Response<List<Album>> response) {
        getBus().post(new SuccessEvent(response.body()));
    }

    @Override
    protected void onResponseUnsuccessful(Response<List<Album>> response, int code) {
        getBus().post(new FailEvent());
    }

    @Override
    protected void onNetworkError() {
        getBus().post(new FailEvent());
    }

    public static class FetchEvent {}
    public static class SuccessEvent {
        public final List<Album> albums;

        public SuccessEvent(List<Album> albums) {
            this.albums = albums;
        }
    }
    public static class FailEvent {}
}
