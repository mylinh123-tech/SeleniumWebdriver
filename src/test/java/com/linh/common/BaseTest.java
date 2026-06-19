package com.linh.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {
    public WebDriver driver;
    @BeforeMethod
    public void CreateBrowser(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

    }
    @AfterMethod
    public void CloseBrowser(){
        driver.quit();
    }
    public void sleep(double second){
        try {
            Thread.sleep((long) second*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
