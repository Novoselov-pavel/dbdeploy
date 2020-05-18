package com.npn.updater.drivers;

import com.npn.updater.interfaces.InputFileInterface;
import com.npn.updater.model.DbOperationItem;
import com.npn.updater.model.DbType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.*;

/**XML PARSER
 *
 */
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
        nodeName.add("item");
    }


    @Override
    public DbOperationItem getOperation(String filePath) throws Exception {
        logger.debug("DbOperationItem");

        logger.info("Start reading XML: " + filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(filePath);

        DbType type = DbType.getDbType(getFirstElementTextByTagName(nodeName.get(0),document.getDocumentElement()));
        String database = getFirstElementTextByTagName(nodeName.get(1),document.getDocumentElement());
        String host = getFirstElementTextByTagName(nodeName.get(2),document.getDocumentElement());
        String port = getFirstElementTextByTagName(nodeName.get(3),document.getDocumentElement());
        Properties properties = new Properties();
        properties.putAll(getMapFromItems(nodeName.get(4),document.getDocumentElement()));

        List<String> sqlList = getItems(nodeName.get(5),document.getDocumentElement());

        DbOperationItem operationItem = new DbOperationItem();
        operationItem.setDbType(type);
        operationItem.setDatabase(database);
        operationItem.setHost(host);
        operationItem.setPort(port);
        operationItem.setConnectionProperties(properties);
        operationItem.setSqlQuery(sqlList);
        logger.info("End reading XML: " + filePath);
        return operationItem;

    }

    /**
     * Выдает список расширений файлов которые обрабатываются текущей реализацией интерфейса
     *
     * @return unmodificable set
     */
    @Override
    public Set<String> getExtension() {
        Set<String> set = new TreeSet<>(Comparator.naturalOrder());
        set.add("xml");
        return Collections.unmodifiableSet(set);
    }

    /**Возвращает {@link NodeList}  элементов с соответсвующими tagName
     *
     * @param tagName
     * @param element
     * @return
     */
    private NodeList getElementsByTagName(final String tagName, final Element element) {
        return element.getElementsByTagName(tagName);
    }

    /**Возвращает текст первого элемента с соответсвующими tagName или null
     *
     * @param tagName
     * @param element
     * @return
     */
    private String getFirstElementTextByTagName(final String tagName, final Element element) {
        logger.debug("getFirstElementTextByTagName");
        NodeList list = getElementsByTagName(tagName, element);
        if (list!=null && list.item(0)!=null) {
            return list.item(0).getTextContent();
        } else {
            return null;
        }
    }


    /**Возвращает Map с распознанными парами ключ-значение у элементов {@code <item>} (nodeName.get(6)) который является дочерним элементом объекта с tagName
     * элементы текст которых не содержит "=" или не содержит значение будут проигнорированны.
     *
     * @param tagName
     * @param element
     * @return
     */
    private Map<String,String> getMapFromItems(final String tagName, final Element element) {
        logger.debug("getMapFromItems");
        Map<String,String> map = new HashMap<>();
        List<String> getItems = getItems(tagName,element);
        getItems.forEach(x->{
            if (x.contains("=") && (x.indexOf("=")+1)<x.length()){
                String key = x.substring(0, x.indexOf("="));
                String value = x.substring(x.indexOf("=")+1);
                map.put(key,value);
            }
        });
        return map;
    }

    /**Возвращает {@link List} с текстом у элемента {@code <item>} (nodeName.get(6)) который является дочерним элементом объекта с tagName
     * <blockquote><pre>
     *  {@code <tagName>
     *             <item>text1</item>
     *             <item>text2</item>
     *         </tagName>}
     * </pre></blockquote>
     * @param tagName имя корневого тега
     * @param element элемент в котором происходит поиск.
     * @return {@link List} с текстовой частью элемента
     */
    private List<String> getItems(final String tagName, final Element element) {
        List<String> itemsString = new ArrayList<>();

        NodeList list = getElementsByTagName(tagName, element);
        if (list!=null && list.item(0)!=null) {
            NodeList childList = list.item(0).getChildNodes();
            for (int i = 0; i <childList.getLength(); i++) {
                if (childList.item(i).getNodeName().equalsIgnoreCase(nodeName.get(6))) {
                    String nodeText = childList.item(i).getTextContent();
                    itemsString.add(nodeText);
                }
            }
        }
        return  itemsString;
    }


}
