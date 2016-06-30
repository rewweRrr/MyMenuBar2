package ru.netcracker.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.netcracker.server.services.XmlItemLoaderService;
import ru.netcracker.shared.Item;

import java.util.List;

/**
 * Created by nivo0616 on 30.06.2016
 */
@Controller
public class XmlController {

    private XmlItemLoaderService xmlItemLoaderService;

    @Autowired
    public void setXmlItemLoaderService(XmlItemLoaderService xmlItemLoaderService) {
        this.xmlItemLoaderService = xmlItemLoaderService;
    }

    @RequestMapping(value = "/getItems", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<Item> getItems() throws Exception {
        return xmlItemLoaderService.items();
    }
}
