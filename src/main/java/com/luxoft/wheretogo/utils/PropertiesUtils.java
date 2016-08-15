package com.luxoft.wheretogo.utils;

import com.luxoft.wheretogo.mailer.Mailer;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by bobbi on 15.08.16.
 */
public class PropertiesUtils {
    private final static String APP_PROPS_FILE_NAME = "application.properties";
    public static Properties appProp = new Properties();

    static {
        try {
            appProp.load(Mailer.class.getClassLoader().getResourceAsStream(APP_PROPS_FILE_NAME));
        }
        catch (IOException | NullPointerException e) {
        }
    }
    public static String getProp(String prop){
        return (String)appProp.get(prop);
    }
}
