package com;

import java.io.IOException;
import java.util.Properties;

/**
 * 静态代码块饿汉式（适合复杂实例化）
 */
public class Singleton3 {
    public static final Singleton3 INSTANCE;
    private String info;
    static {

        try {
            Properties properties = new Properties();
            properties.load(Singleton3.class.getClassLoader().getResourceAsStream("file.properties"));
            INSTANCE = new Singleton3(properties.getProperty("info"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private Singleton3(String info){
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String toString(){
        return "Singleton3 [info=" + info + "]";
    }
}
