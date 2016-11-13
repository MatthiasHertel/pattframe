package org.blueberry.kentuckyderby;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import java.net.URLDecoder;

public class Configuration {

    private Properties properties = new Properties();
    private String propertiesFilename = "config/application.properties";
    private String error;

    public boolean load() {
        try {
            ClassLoader loader = Configuration.class.getClassLoader();
            String appPropertiesFilepath = URLDecoder.decode(loader.getResource(propertiesFilename).getPath(), "UTF-8");
            properties.load(new FileInputStream(appPropertiesFilepath));
        }
        catch (IOException e) {
            error = e.getMessage();
            return false;
        }
        return true;
    }

    public String getItem(String name) {
        return properties.getProperty(name);
    }

    public void printError(PrintStream stream) {
        stream.println(error);
    }
}
