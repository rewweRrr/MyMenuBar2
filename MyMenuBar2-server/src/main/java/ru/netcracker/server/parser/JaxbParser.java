package ru.netcracker.server.parser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.InputStream;

/**
 * Created by nivo0616 on 29.06.2016
 */
public interface JaxbParser {
    Object getObject(File file, Class c) throws JAXBException;
    Object getObject(InputStream inputStream, Class c) throws JAXBException;
    void saveObject(File file, Object o) throws JAXBException;
}
