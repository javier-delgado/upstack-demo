package com.javierdelgado.upstack_demo.network;


import com.javierdelgado.upstack_demo.models.Album;
import com.javierdelgado.upstack_demo.models.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * API que contiene los endpoints con los que se comunica la app
 */
public interface Api {
    @GET("albums")
    Call<List<Album>> getAlbums();

    @GET("albums/{album_id}/photos")
    Call<List<Photo>> getPhotos(@Path("album_id") int id);
}
