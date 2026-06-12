package com.linh.Bai10_Annotations.DemoAutomation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest1 {
    WebDriver driver ;

    @BeforeClass
    public void setUp(){
        System.out.println("Set up environment... ");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }
    @AfterClass
    public void Teardown(){
        driver.quit();
        System.out.println("Close browser");

    }
    @Test(priority = 1)
    public void Login() throws InterruptedException {
        System.out.println("Start login page");
        driver.get("https://crm.anhtester.com/admin/authentication");
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("admin@example.com");
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
        System.out.println("Login success");

    }

    @Test(priority = 2)
    public void LoginFail() throws InterruptedException {
        System.out.println("Start logout page");
        driver.findElement(By.xpath("//li[contains(@class, \"profile\")]//a//img")).click();
        driver.findElement(By.linkText("Logout")).click();


        System.out.println("Start login again");
        driver.get("https://crm.anhtester.com/admin/authentication");
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("admin@example.com");
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("123456345");
        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
        System.out.println("Login fail");

    }





}
