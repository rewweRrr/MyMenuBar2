package ru.netcracker.client.list.providers;

import ru.netcracker.shared.Item;

import java.util.List;

/**
 * Created by nivo0616 on 15.06.2016
 */
public interface ItemProvider {
    void get(ItemCallBack<List<Item>> itemCallBack);
}
