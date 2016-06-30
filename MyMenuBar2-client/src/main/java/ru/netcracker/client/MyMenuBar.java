package ru.netcracker.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * Created by rewweRrr on 04.04.2016
 */
public class MyMenuBar implements EntryPoint {
    public void onModuleLoad() {
        UBMyMenuBar menuBar = new UBMyMenuBar();
        RootPanel.get().add(menuBar);
    }
}
