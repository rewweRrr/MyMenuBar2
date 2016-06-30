package ru.netcracker.shared;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by rewweRrr on 04.04.2016
 */
@SuppressWarnings("unused")
@XmlRootElement(name = "person")
@XmlType(propOrder = {"id","name"})
public class Item {

    private String id;
    private String name;

    public Item() {
    }

    public Item(String id, String name) {
        this.id = id;
        this.name = name;

    }

    public String getId() {
        return id;
    }

    @XmlElement
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }
}
