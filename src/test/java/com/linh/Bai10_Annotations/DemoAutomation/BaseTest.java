package com.linh.Bai10_Annotations.DemoAutomation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.time.Duration;

public class BaseTest {
    WebDriver driver ;
    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Connect Database");
        System.out.println("Connect API Jira");
        System.out.println("Connect API Momo");
    }

    @BeforeMethod
    public void setUp(){
        System.out.println("Set up environment... ");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }
    @AfterMethod
    public void Teardown(){
        driver.quit();
        System.out.println("Close browser");
        System.out.println("------------------------------------");

    }
}
