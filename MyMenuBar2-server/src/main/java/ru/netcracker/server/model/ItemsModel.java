package ru.netcracker.server.model;

import ru.netcracker.shared.Item;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by nivo0616 on 29.06.2016
 */
@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemsModel {
    @XmlElement(name = "item")
    private List<Item> items = null;

    public List<Item> getItems() {
        return items;
    }

    @SuppressWarnings("unused")
    public void setItems(List<Item> items) {
        this.items = items;
    }
}
