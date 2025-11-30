package com.solvd.app;

public class ApiBase {

    public static String getBaseUrl() {
        String url = System.getProperty("api.url");
        if (url==null) {
            url= "https://reqres.in/api";
        }
        return url;
    }

    public static String prop(String key, String defaultVal) {
        String val = System.getProperty(key);
        return val == null ? defaultVal :val;
    }
}
