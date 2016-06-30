package ru.netcracker.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import ru.netcracker.client.list.MyList;
import ru.netcracker.client.list.gwt.MyListImpl;
import ru.netcracker.client.list.js.MyListJsImpl;
import ru.netcracker.client.list.providers.ItemCallBack;
import ru.netcracker.client.list.providers.ItemProvider;
import ru.netcracker.client.services.ItemService;
import ru.netcracker.client.wrapprs.ItemWrapper;
import ru.netcracker.shared.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rewweRrr on 04.04.2016
 */
public class UBMyMenuBar extends Composite {
    interface UBMyMenuBarUiBinder extends UiBinder<FlowPanel, UBMyMenuBar> {
    }

    private static UBMyMenuBarUiBinder ourUiBinder = GWT.create(UBMyMenuBarUiBinder.class);

    @UiField
    MyListImpl list;
    @UiField
    MyListJsImpl jsList;
    @UiField
    ListBox listsBox;
    @UiField
    CheckBox chooseProvider;
    @UiField
    VerticalPanel providerPanel;
    @UiField
    RadioButton radioClient;
    @UiField
    RadioButton radioServer;
    @UiField
    Button providerButton;
    @UiField
    CheckBox clientProviderType;
    @UiField
    TextBox idBox;
    @UiField
    TextBox nameBox;
    @UiField
    Button addButton;
    @UiField
    Button clearButton;
    @UiField
    Button removeButton;

    private MyList currentList;

    public MyListJsImpl getJsList() {
        if (jsList.getJsItemsWrapper() == null) {
            jsList.setJsItemsWrapper(new ArrayList<ItemWrapper>());
            loadJsListResources();
        }
        return jsList;
    }

    public MyListImpl getList() {
        if (list.getItemsWrapper() == null) {
            list.setItemsWrapper(new ArrayList<ItemWrapper>());
            loadListResources();
        }
        return list;
    }

    public UBMyMenuBar() {
        initWidget(ourUiBinder.createAndBindUi(this));
        list.getElement().setId("gwt-list");
        jsList.getElement().setId("js-list");

        jsList.getElement().setAttribute("hidden", "true");

        providerPanel.setVisible(false);

        listsBox.addItem("gwt-list");
        listsBox.addItem("js-list");

        setCurrentList(listsBox.getSelectedItemText());
    }


    @SuppressWarnings("UnusedParameters")
    @UiHandler("listsBox")
    public void onClickBox(ClickEvent event) {
        changeList(listsBox.getSelectedItemText());
    }

    @SuppressWarnings("UnusedParameters")
    @UiHandler("addButton")
    public void onClickAddBtn(ClickEvent event) {
        if (!idBox.getText().equals("") && !nameBox.getText().equals("")) {
            currentList.add(new Item(idBox.getText(), nameBox.getText()));
        } else if (idBox.getText().equals("") && !nameBox.getText().equals("")) {
            currentList.add(nameBox.getText());
        } else {
            Window.alert("Enter person name");
        }
    }

    @SuppressWarnings("UnusedParameters")
    @UiHandler("clearButton")
    public void onClickClearBtn(ClickEvent event) {
        currentList.clear();
    }

    @SuppressWarnings("UnusedParameters")
    @UiHandler("removeButton")
    public void onClickRemoveBtn(ClickEvent event) {
        if (!idBox.getText().equals("")) {
            currentList.remove(new Item(idBox.getText(), nameBox.getText()));
        }
    }

    @UiHandler("chooseProvider")
    public void onValueChange(ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            providerPanel.setVisible(true);
            clientProviderType.setVisible(true);
            radioClient.setValue(true);
        } else {
            providerPanel.setVisible(false);
            clientProviderType.setVisible(false);
        }
    }

    @SuppressWarnings("UnusedParameters")
    @UiHandler("radioClient")
    public void onClickClientRadio(ClickEvent event) {
        clientProviderType.setVisible(true);
    }

    @SuppressWarnings("UnusedParameters")
    @UiHandler("radioServer")
    public void onClickServerRadio(ClickEvent event) {
        clientProviderType.setVisible(false);
    }

    @SuppressWarnings("UnusedParameters")
    @UiHandler("providerButton")
    public void onClickProviderBtn(ClickEvent event) {
        if (radioClient.getValue()) {
            if (clientProviderType.getValue()) {
                currentList.setProvider(new ItemProvider() {

                    @Override
                    public void get(final ItemCallBack<List<Item>> itemCallBack) {

                        final List<Item> items = new ArrayList<>();

                        items.add(new Item("1", "Async Provider"));
                        items.add(new Item("2", "Async Provider2"));

                        Timer t = new Timer() {
                            public void run() {
                                itemCallBack.onSuccess(items);
                            }
                        };
                        t.schedule(1500);
                    }
                });
            } else {
                currentList.setProvider(new ItemProvider() {

                    @Override
                    public void get(final ItemCallBack<List<Item>> itemCallBack) {
                        final List<Item> items = new ArrayList<>();
                        items.add(new Item("1", "Sync Provider"));
                        items.add(new Item("2", "Sync Provider2"));

                        itemCallBack.onSuccess(items);
                    }
                });
            }
        }

        if (radioServer.getValue()) {

            currentList.setProvider(new ItemProvider() {
                @Override
                public void get(final ItemCallBack<List<Item>> itemCallBack) {

                    ItemService service = GWT.create(ItemService.class);
                    service.getItems(new MethodCallback<List<Item>>() {
                        @Override
                        public void onFailure(Method method, Throwable throwable) {
                            throw new RuntimeException("call failed");
                        }

                        @Override
                        public void onSuccess(Method method, List<Item> items) {
                            itemCallBack.onSuccess(items);
                        }
                    });
                }
            });
        }
    }

    private void changeList(String listId) {
        setCurrentList(listId);
        hiddenAllLists();
        showList(listId);
    }

    private void showList(String listId) {
        DOM.getElementById(listId).removeAttribute("hidden");
    }

    private void hiddenAllLists() {
        for (int i = 0; i < listsBox.getItemCount(); i++) {
            DOM.getElementById(listsBox.getItemText(i)).setAttribute("hidden", "true");
        }
    }

    private void setCurrentList(String listId) {
        if (listId.equals(list.getElement().getId())) {
            currentList = getList();
        } else {
            currentList = getJsList();
        }
    }

    private void loadListResources() {

        ItemService service = GWT.create(ItemService.class);
        service.getItems(new MethodCallback<List<Item>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                throw new RuntimeException("call failed");
            }

            @Override
            public void onSuccess(Method method, List<Item> items) {
                list.replaceAll(items);
            }
        });
    }

    private void loadJsListResources() {

        ItemService service = GWT.create(ItemService.class);
        service.getItems(new MethodCallback<List<Item>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                throw new RuntimeException("call failed");
            }

            @Override
            public void onSuccess(Method method, List<Item> items) {
                jsList.replaceAll(items);
            }
        });
    }
}