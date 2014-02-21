package com.epam.util;

import org.apache.log4j.Logger;

import java.util.ResourceBundle;

public class PropertiesBundle {

    private static final Logger log = Logger.getLogger(PropertiesBundle.class);

    private static final ResourceBundle settings = ResourceBundle.getBundle("settings");

    /**
     * Return value of key from property fail.
     *
     * @param key - String type.You must write key witch describe regex in fail properties.
     * @return String type - value of key in property fail.
     */
    public static String getValue(String key) {
        return settings.getString(key);
    }
}
