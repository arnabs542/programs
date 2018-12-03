package com.raj.test;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

/**
 * Tests for IRO service client functionality
 *
 */
public class IROServiceClientTests {

    public static Properties envProps = new Properties();
    public static final String propertyPath = "storm-local.properties";
    private static ClassLoader classLoader;

    /**
     * Load env properties
     */
    @BeforeClass
    public static void init() {
        try {
            classLoader = IROServiceClientTests.class.getClassLoader();
            envProps.load(classLoader.getResourceAsStream(propertyPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {

    }
}
