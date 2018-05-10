package com.javierdelgado.upstack_demo;

import android.app.Application;

import com.javierdelgado.upstack_demo.network.NetworkExchangersSubscriber;
import com.javierdelgado.upstack_demo.network.RestClient;

/**
 * Created by javier on 9/22/17.
 */

public class UpstackDemo extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RestClient.init(this);
        NetworkExchangersSubscriber.registerAll();
    }
}
