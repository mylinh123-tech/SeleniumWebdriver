package com.linh.keywords;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.plaf.PanelUI;

public class WebUI {
    public static void clickElement (WebDriver driver, By locator){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px solid red'", driver.findElement(locator));
        driver.findElement(locator).click();

    }
    public static void setText(WebDriver driver, By locator, String text){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px solid red'", driver.findElement(locator));
        driver.findElement(locator).sendKeys(text);
    }

}
