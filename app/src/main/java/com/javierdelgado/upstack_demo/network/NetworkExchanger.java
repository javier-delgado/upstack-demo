package com.javierdelgado.upstack_demo.network;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Clase base para crear Network Exchangers (Senders y fetchers)
 */
public abstract class NetworkExchanger<T> {
    protected abstract void onResponseSuccessful(Response<T> response);
    protected abstract void onNetworkError();
    protected abstract void onResponseUnsuccessful(Response<T> response, int code);
    private ArrayList<Call> pendingCalls = new ArrayList<>();

    protected void startHttpRequest(Call<T> call) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {

                if (response.isSuccessful()) {
                    onResponseSuccessful(response);
                } else {
                    onResponseUnsuccessful(response, response.code());
                }
                pendingCalls.remove(call);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                t.printStackTrace();
                onNetworkError();
                pendingCalls.remove(call);
            }
        });
        pendingCalls.add(call);
    }

    protected void cancelPendingCalls() {
        for(Call call : pendingCalls) {
            call.cancel();
        }
        pendingCalls.clear();
    }

    protected EventBus getBus() {
        return EventBus.getDefault();
    }

    protected Api getApi() {
        return RestClient.getInstance();
    }

}
