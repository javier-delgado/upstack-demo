package com.javierdelgado.upstack_demo.network;

import org.greenrobot.eventbus.EventBus;

import com.javierdelgado.upstack_demo.network.fetchers.AlbumsFetcher;
import com.javierdelgado.upstack_demo.network.fetchers.PhotosFetcher;

/**
 * Esta clase registra en el bus a todos los NetworkExchangers
 * No olvidar agregar los nuevos senders y fetchers aqui!
 */
public final class NetworkExchangersSubscriber {
    private final static Class[] sendersAndFetchers = new Class[] {
            AlbumsFetcher.class,
            PhotosFetcher.class
    };

    public static void registerAll(){
        for (Class senderOrFetcher : sendersAndFetchers) {
            try {
                EventBus.getDefault().register(senderOrFetcher.newInstance());
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
