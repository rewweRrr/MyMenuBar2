package ru.netcracker.client.wrapprs;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.DOM;
import ru.netcracker.shared.Item;

/**
 * Created by nivo0616 on 21.06.2016
 */
public class ItemWrapper {

    private Item item;

    public Item getItem() {
        return item;
    }

    public ItemWrapper(String id, String name) {
        this.item = new Item(id, name);
    }

    public ItemWrapper on(String eventName, Function function) {
        Element currentElement = DOM.getElementById(item.getId());
        GQuery.$(currentElement).on(eventName, function);

        return this;
    }

    public ItemWrapper off(String eventName) {
        Element currentElement = DOM.getElementById(item.getId());
        GQuery.$(currentElement).off(eventName);

        return this;
    }
}
