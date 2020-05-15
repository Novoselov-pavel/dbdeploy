package com.npn.updater.drivers;

import com.npn.updater.interfaces.InputFileInterface;
import com.npn.updater.model.DbOperationItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlDriver implements InputFileInterface {
    private static final Logger logger = LoggerFactory.getLogger(XmlDriver.class);

    private static final List<String> nodeName = new ArrayList<>();

    static {
        nodeName.add("dbtype");
        nodeName.add("database");
        nodeName.add("host");
        nodeName.add("port");
        nodeName.add("connectionProperties");
        nodeName.add("query");
    }


    @Override
    public DbOperationItem getOperation(String filePath) {
        logger.debug("DbOperationItem");
        try {
            logger.info("Start reading XML: " + filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filePath);

            NodeList list = getElementsByTagName(nodeName.get(0),document.getDocumentElement());
            System.out.println("");
            ///TODO

        }  catch (ParserConfigurationException e) {
            e.printStackTrace(); ///TODO
        } catch (IOException e) {
            e.printStackTrace(); ///TODO
        } catch (SAXException e) {
            e.printStackTrace();  ///TODO
        }


        return null;
    }

    private NodeList getElementsByTagName(final String tagName, final Element element) {
        return element.getElementsByTagName(tagName);
    }

    private String getFirstElementTextByTagName(final String tagName, final Element element) {
        NodeList list = getElementsByTagName(tagName, element);
        if (list!=null && list.item(0)!=null) {
            return list.item(0).getTextContent();
        } else {
            return null;
        }
    }

}
