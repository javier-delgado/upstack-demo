package com.javierdelgado.upstack_demo.network.fetchers;

import com.javierdelgado.upstack_demo.models.Photo;
import com.javierdelgado.upstack_demo.network.NetworkExchanger;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import retrofit2.Response;

/**
 * Created by javier on 9/11/17.
 */

public class PhotosFetcher extends NetworkExchanger<List<Photo>> {

    @Subscribe
    public void fetch(FetchEvent event) {
        startHttpRequest(getApi().getPhotos(event.albumId));
    }

    @Override
    protected void onResponseSuccessful(Response<List<Photo>> response) {
        getBus().post(new SuccessEvent(response.body()));
    }

    @Override
    protected void onResponseUnsuccessful(Response<List<Photo>> response, int code) {
        getBus().post(new FailEvent());
    }

    @Override
    protected void onNetworkError() {
        getBus().post(new FailEvent());
    }

    public static class FetchEvent {
        public final int albumId;

        public FetchEvent(int albumId) {
            this.albumId = albumId;
        }
    }
    public static class SuccessEvent {
        public final List<Photo> photos;

        public SuccessEvent(List<Photo> photos) {
            this.photos = photos;
        }
    }
    public static class FailEvent {}
}
