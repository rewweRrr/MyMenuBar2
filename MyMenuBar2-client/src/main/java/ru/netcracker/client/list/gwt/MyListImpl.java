package ru.netcracker.client.list.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ru.netcracker.client.list.MyList;
import ru.netcracker.client.list.providers.ItemCallBack;
import ru.netcracker.client.list.providers.ItemProvider;
import ru.netcracker.client.wrapprs.ItemWrapper;
import ru.netcracker.shared.Item;

import java.util.List;

/**
 * Created by rewweRrr on 05.04.2016
 */
public class MyListImpl extends Composite implements MyList {
    interface MyListUiBinder extends UiBinder<HTMLPanel, MyListImpl> {
    }

    private static MyListUiBinder ourUiBinder = GWT.create(MyListUiBinder.class);

    @UiField
    Element ul;

    private List<ItemWrapper> itemsWrapper;

    public List<ItemWrapper> getItemsWrapper() {
        return itemsWrapper;
    }

    public void setItemsWrapper(List<ItemWrapper> itemsWrapper) {
        this.itemsWrapper = itemsWrapper;
    }

    public MyListImpl() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }


    private String generateId() {
        return HTMLPanel.createUniqueId();
    }

    public void add(Item item) {
        Element li = DOM.createElement("li");

        if (DOM.getElementById(item.getId()) != null) {
            add(item.getName());
        } else {
            li.setId(item.getId());
            li.setInnerText(item.getName());
            DOM.appendChild(ul, li);

            itemsWrapper.add(new ItemWrapper(item.getId(), item.getName()));
        }
    }

    public void add(String name) {
        Element li = DOM.createElement("li");
        String generatedId = generateId();

        li.setId(generatedId);
        li.setInnerText(name);

        DOM.appendChild(ul, li);
        itemsWrapper.add(new ItemWrapper(generatedId, name));
    }

    public ItemWrapper get(String id) {
        for (ItemWrapper wrapper : itemsWrapper) {
            if (wrapper.getItem().getId().equals(id)) {
                return wrapper;
            }
        }
        return null;
    }

    public void remove(Item item) {
        Element li = DOM.getElementById(item.getId());
        if (li != null) {
            li.getParentElement().removeChild(li);
            removeItemWrapper(item.getId());
        }
    }

    private void removeItemWrapper(String id) {
        for (int i = 0; i < itemsWrapper.size(); i++) {
            if (itemsWrapper.get(i).getItem().getId().equals(id)) {
                itemsWrapper.remove(i);
            }
        }
    }

    public void clear() {

        while (ul.hasChildNodes()) {
            ul.removeChild(ul.getLastChild());
            itemsWrapper.clear();
        }
    }

    public void replaceAll(List<Item> items) {
        clear();
        for (Item item : items) {
            add(item);
        }
    }

    public void setProvider(ItemProvider provider) {

        provider.get(new ItemCallBack<List<Item>>() {
            public void onFailure(Throwable caught) {
                throw new RuntimeException("can't provide items");
            }

            public void onSuccess(List<Item> result) {
                for (Item item : result) {
                    add(item);
                }
            }
        });
    }
}