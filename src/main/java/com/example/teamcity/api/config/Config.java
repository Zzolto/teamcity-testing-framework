package com.example.teamcity.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final String CONFIG_PROPERTIES = "config.properties";
    private static Config config;
    private Properties properties;

    public Config(){
        properties = new Properties();
        loadProperties(CONFIG_PROPERTIES);
    }

    public static Config getConfig(){
        if(config == null){
            config = new Config();
        }
        return config;
    }

    private void loadProperties(String fileName){
        try {
            InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(fileName);
            if(inputStream == null){
                System.out.println("File not found: " + fileName);
            }
            properties.load(inputStream);
        } catch (IOException e){
            System.err.println("Error loading properties file: " + fileName);
        }
    }

    public static String getProperties(String key){
        return getConfig().properties.getProperty(key);
    }

}
