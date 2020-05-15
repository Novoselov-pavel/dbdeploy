package com.npn.updater.drivers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XmlDriverTest {
    private final String path = "/home/pavel/IdeaProjects/dbdeploy/src/test/java/test.xml";

    @Test
    void getOperation() {
        XmlDriver xmlDriver = new XmlDriver();
        xmlDriver.getOperation(path);

    }
}