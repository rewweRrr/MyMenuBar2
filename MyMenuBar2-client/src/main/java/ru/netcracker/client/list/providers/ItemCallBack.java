package ru.netcracker.client.list.providers;

/**
 * Created by nivo0616 on 15.06.2016
 */
public interface ItemCallBack<T> {
    void onSuccess(T result);

    void onFailure(Throwable caught);
}
