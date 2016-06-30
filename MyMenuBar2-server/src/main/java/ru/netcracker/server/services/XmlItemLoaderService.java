package ru.netcracker.server.services;

import ru.netcracker.shared.Item;

import java.util.List;

/**
 * Created by nivo0616 on 30.06.2016
 */
public interface XmlItemLoaderService {
    List<Item> items() throws Exception;
}
