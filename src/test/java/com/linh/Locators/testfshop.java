package com.linh.Locators;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class testfshop {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://fptshop.com.vn/");
        driver.findElement(By.xpath("//input[@name ='search' and @type = 'text']")).sendKeys("iphone 17");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@type ='submit']")).click();
        driver.findElement(By.xpath("(//img[@alt='Cookie']/ancestor::div)/following-sibling::button")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[text()='Để sau']")).click();
        driver.findElement(By.xpath("//input[@name ='search' and @type = 'text']")).clear();
        WebElement search_el =  driver.findElement(By.xpath("//input[@name ='search' and @type = 'text']"));
        search_el.sendKeys("screen");

        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@type ='submit']")).click();

  /*      JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
////// Scroll tới element
////        js.executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(5000);
        search_el =  driver.findElement(By.xpath("//input[@name ='search' and @type = 'text']"));
        js.executeScript("arguments[0].scrollIntoView(true);", search_el);
        Thread.sleep(1000);*/

        Actions actions = new Actions(driver);
        WebElement category = driver.findElement(By.xpath("//span[text()=' Danh mục']"));
        actions.moveToElement(category).perform();
        Thread.sleep(5000);

       // driver.findElement(By.xpath("//button[@aria-label='Danh mục']")).click();

        WebElement laptopItem = driver.findElement(By.xpath("//a[@href='/may-tinh-xach-tay']/descendant::span[text()='Laptop']"));
        actions.moveToElement(laptopItem).perform();
        Thread.sleep(5000);


        driver.findElement(By.xpath("//a[@href='https://fptshop.com.vn/may-tinh-xach-tay/apple-macbook']//div//div//img[1]")).click();
        Thread.sleep(3000);

        driver.findElement(By.xpath("//label[text()='Trên 30 triệu']")).click();
        driver.findElement(By.xpath("//button[text()='MacBook Air M5']")).click();
        driver.findElement(By.xpath("//div[@class ='rc-slider-handle rc-slider-handle-2']"));



        driver.close();
        driver.quit();
    }
}
