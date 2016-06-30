package ru.netcracker.client.list.js;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ru.netcracker.client.list.MyList;
import ru.netcracker.client.list.providers.ItemCallBack;
import ru.netcracker.client.list.providers.ItemProvider;
import ru.netcracker.client.wrapprs.ItemWrapper;
import ru.netcracker.shared.Item;

import java.util.List;

/**
 * Created by nivo0616 on 06.06.2016
 */
public class MyListJsImpl extends Composite implements MyList {

    interface MyListJsImplUiBinder extends UiBinder<HTMLPanel, MyListJsImpl> {
    }

    private static MyListJsImplUiBinder ourUiBinder = GWT.create(MyListJsImplUiBinder.class);

    @UiField
    Element ul;
    private List<ItemWrapper> JsItemsWrapper;

    public List<ItemWrapper> getJsItemsWrapper() {
        return JsItemsWrapper;
    }

    public void setJsItemsWrapper(List<ItemWrapper> jsItemsWrapper) {
        this.JsItemsWrapper = jsItemsWrapper;
    }

    public MyListJsImpl() {
        initWidget(ourUiBinder.createAndBindUi(this));
        ul.setId("jsList");
    }

    public void add(Item item) {
        JsItemsWrapper.add(addToJsList(item));
    }

    private native ItemWrapper addToJsList(Item item) /*-{
        var $ulElement = $wnd.$(this.@MyListJsImpl::ul);
        var $liElement = $ulElement.myMenu("add", {
            id: item.@ru.netcracker.shared.Item::getId()(),
            name: item.@ru.netcracker.shared.Item::getName()()
        });

        return @ru.netcracker.client.wrapprs.ItemWrapper::new(Ljava/lang/String;Ljava/lang/String;)($liElement.attr("id"), $liElement.text());
    }-*/;


    public void add(String name) {
        JsItemsWrapper.add(addToJsList(name));
    }

    private native ItemWrapper addToJsList(String name) /*-{
        var $ulElement = $wnd.$(this.@MyListJsImpl::ul);
        var $liElement = $wnd.$("<li>").uniqueId();

        $liElement.html(name).appendTo($ulElement);

        return @ru.netcracker.client.wrapprs.ItemWrapper::new(Ljava/lang/String;Ljava/lang/String;)($liElement.attr("id"), name);
    }-*/;

    public native ItemWrapper get(String id) /*-{
        var $liElement = $wnd.$(this.@MyListJsImpl::ul).myMenu().get(id);

        return @ru.netcracker.client.wrapprs.ItemWrapper::new(Ljava/lang/String;Ljava/lang/String;)($liElement.attr("id"), $liElement.text());
    }-*/;

    public void remove(Item item) {
        for (int i = 0; i < JsItemsWrapper.size(); i++) {
            if (JsItemsWrapper.get(i).getItem().getId().equals(item.getId())) {
                JsItemsWrapper.remove(i);
            }
        }
        removeFromJsList(item);
    }

    private native void removeFromJsList(Item item) /*-{
        $wnd.$(this.@MyListJsImpl::ul).myMenu("remove", {
            id: item.@ru.netcracker.shared.Item::getId()(),
            name: item.@ru.netcracker.shared.Item::getName()()
        })
    }-*/;

    public void clear() {
        JsItemsWrapper.clear();
        clearJsList();
    }

    private native void clearJsList() /*-{
        $wnd.$(this.@MyListJsImpl::ul).myMenu().clear();
    }-*/;

    public void replaceAll(List<Item> items) {
        clear();

        for (Item item : items) {
            add(item);
        }
    }

    public native void setProvider(ItemProvider provider) /*-{
        var _this = this;
        $wnd.$(this.@MyListJsImpl::ul).myMenu().setProvider({
            get: function ($deferred) {
                _this.@MyListJsImpl::setProviderDeferred(*)(provider, $deferred)
            }
        });
    }-*/;

    private void setProviderDeferred(ItemProvider provider, final JavaScriptObject deferred) {
        provider.get(new ItemCallBack<List<Item>>() {
            public void onSuccess(List<Item> result) {
                deferredResolve(itemsToJsObject(result), deferred);
            }

            public void onFailure(Throwable caught) {
                throw new RuntimeException("can't provide items");
            }
        });
    }

    private native JavaScriptObject itemsToJsObject(List<Item> items)/*-{
        var items = new Array(items.@List::size()());

        for (var i = 0; i < items.@List::size()(); i++) {
            var item = items.@List::get(*)(i);
            items[i] = {
                id: item.@ru.netcracker.shared.Item::getId()(),
                name: item.@ru.netcracker.shared.Item::getName()()
            };
        }
        return items;
    }-*/;

    private native void deferredResolve(JavaScriptObject items, JavaScriptObject deferred) /*-{
        deferred.resolve(items);
    }-*/;

}