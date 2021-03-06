package com.npn.dbdeploy.drivers;

import com.npn.dbdeploy.model.DbOperationItem;
import com.npn.dbdeploy.model.DbType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class XmlDriverTest {
    private final static String path = "/home/pavel/IdeaProjects/dbdeploy/src/test/java/test.xml";
    private final static DbOperationItem testItem = new DbOperationItem();

    static {
        testItem.setDbType(DbType.POSTGRES);
        testItem.setPort("5432");
        testItem.setHost("localhost");
        testItem.setDatabase("test");
        Properties properties = new Properties();
        properties.put("user","dbadmin");
        properties.put("password", "3hLVnpUvhddky");
        testItem.setConnectionProperties(properties);
        List<String> sqlList = new ArrayList<>();
        sqlList.add("DROP TABLE IF EXISTS a1;");
        sqlList.add("CREATE TABLE a1 (id bigint PRIMARY KEY, name varchar(255));");
        testItem.setSqlQuery(sqlList);

    }


    @Test
    void getOperation() {
        try {
            XmlDriver xmlDriver = new XmlDriver();
            DbOperationItem result = xmlDriver.getOperation(path);
            assertEquals(testItem,result);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void isFileHasCorrectExtension() {
        String false1 = "/home/pavel/IdeaProjects/dbdeploy/src/test/java/asdasfaf.ssfd";
        String false2 = null;
        String false3 = "";
        XmlDriver xmlDriver = new XmlDriver();
        assertTrue(xmlDriver.isFileHasCorrectExtension(path));
        assertFalse(xmlDriver.isFileHasCorrectExtension(false1));
        //noinspection ConstantConditions
        assertFalse(xmlDriver.isFileHasCorrectExtension(false2));
        assertFalse(xmlDriver.isFileHasCorrectExtension(false3));
    }
}