package ru.netcracker.client.list;

import ru.netcracker.client.list.providers.ItemProvider;
import ru.netcracker.client.wrapprs.ItemWrapper;
import ru.netcracker.shared.Item;

import java.util.List;

/**
 * Created by nivo0616 on 06.06.2016
 */
public interface MyList {

    void add(Item item);

    void add(String name);

    ItemWrapper get(String id);

    void remove(Item item);

    void clear();

    void replaceAll(List<Item> items);

    void setProvider(ItemProvider provider);

}
