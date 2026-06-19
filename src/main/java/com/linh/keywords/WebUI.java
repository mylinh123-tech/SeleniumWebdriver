package com.linh.keywords;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.plaf.PanelUI;
import java.time.Duration;

public class WebUI {
    public static void clickElement (WebDriver driver, By locator){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px solid red'", driver.findElement(locator));
        driver.findElement(locator).click();

    }
    public static void clickElement (WebDriver driver, By locator, int seconds){
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.elementToBeClickable(locator));

        js.executeScript("arguments[0].style.border='3px solid red'", driver.findElement(locator));
        driver.findElement(locator).click();

    }
    public static void setText(WebDriver driver, By locator, String text){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px solid red'", driver.findElement(locator));
        driver.findElement(locator).sendKeys(text);
    }
    public static void setText(WebDriver driver, By locator, String text, int seconds){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        js.executeScript("arguments[0].style.border='3px solid red'", driver.findElement(locator));
        driver.findElement(locator).sendKeys(text);
    }
    public static boolean isElementPresent (WebDriver driver, By locator){
      try{
          driver.findElement(locator).isDisplayed();

      }catch(NoSuchElementException ex)
      {
          return false;
      }
      return true;

    }

    public static boolean isElementPresent (WebDriver driver, By locator, int seconds){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        try{
            driver.findElement(locator).isDisplayed();

        }catch(NoSuchElementException ex)
        {
            return false;
        }
        return true;

    }



}
