package ru.netcracker.server.implementation;

import org.springframework.stereotype.Service;
import ru.netcracker.server.model.ItemsModel;
import ru.netcracker.server.parser.JaxbParser;
import ru.netcracker.server.parser.JaxbParserImpl;
import ru.netcracker.server.services.XmlItemLoaderService;
import ru.netcracker.shared.Item;

import java.io.InputStream;
import java.util.List;

/**
 * Created by nivo0616 on 30.06.2016
 */
@Service
public class XmlItemLoaderServiceImpl implements XmlItemLoaderService {

    private static final String FILE_PATH = "/ru/netcracker/server/files/items.xml";
    private List<Item> items;

    @Override
    public List<Item> items() throws Exception {
        if (items == null) {
            InputStream input = XmlItemLoaderServiceImpl.class.getResourceAsStream(FILE_PATH);

            JaxbParser parser = new JaxbParserImpl();
            ItemsModel model = (ItemsModel) parser.getObject(input, ItemsModel.class);

            items = model.getItems();
        }
        return items;
    }
}
