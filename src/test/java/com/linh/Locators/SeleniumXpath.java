package com.linh.Locators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class SeleniumXpath {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://crm.anhtester.com/admin/authentication");
        driver.findElement(By.xpath("//input[@type =\"email\"]")).sendKeys("admin@example.com");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@type =\"password\"]")).sendKeys("123456");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();
        Thread.sleep(3000);
        driver.quit();


    }

}
