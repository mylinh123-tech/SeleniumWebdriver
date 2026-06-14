package com.linh.utils;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class LocalStorageUtils {
    public static void setValueToLocalStorage (WebDriver driver, String key, String value) {
        String script = String.format("window.localStorage.setItem('%S', '%S');", key, value);
        ((JavascriptExecutor) driver).executeScript(script);


    }
    public static String getValueToLocalStorage (WebDriver driver, String key) {
        String script = String.format("return window.localStorage.getItem('%S');", key);
        return (String)((JavascriptExecutor) driver).executeScript(script);

    }





}