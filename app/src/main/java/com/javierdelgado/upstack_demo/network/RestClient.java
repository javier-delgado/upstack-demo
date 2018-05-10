package com.javierdelgado.upstack_demo.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.javierdelgado.upstack_demo.BuildConfig;
import com.javierdelgado.upstack_demo.R;
import com.javierdelgado.upstack_demo.customComponents.gson.AnnotationExclusionStrategy;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static Api API_SERVICE;
    private static Retrofit RETROFIT;

    private RestClient() { }

    public static void init(final Context context) {
        if (RETROFIT != null){
            Log.w("RestClient", "El método init solo debe ser llamado una vez!");
            return;
        }

        Gson gson = buildGson();
        OkHttpClient okHttpClient = buildOkHttp(context);

        RETROFIT = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_base_url))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        API_SERVICE = RETROFIT.create(Api.class);
    }

    private static OkHttpClient buildOkHttp(Context context) {
        OkHttpClient.Builder okhttpBuilder = new OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES);

        if (BuildConfig.DEBUG) {
            addBetterLogsToRequests(okhttpBuilder);
        }

        return okhttpBuilder.build();
    }

    private static Gson buildGson() {
        return new GsonBuilder()
                .addSerializationExclusionStrategy(new AnnotationExclusionStrategy())
                .create();
    }

    /**
     * Interceptar request para mejores logs en debug
     * @param okhttpBuilder
     */
    private static void addBetterLogsToRequests(OkHttpClient.Builder okhttpBuilder) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpBuilder.addInterceptor(interceptor);
    }

    public static Api getInstance() {
        return API_SERVICE;
    }
    public static Retrofit getRetrofit() { return RETROFIT; }
}
