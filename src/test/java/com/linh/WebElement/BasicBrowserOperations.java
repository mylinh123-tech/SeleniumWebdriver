package com.linh.WebElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class BasicBrowserOperations {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.get("https://crm.anhtester.com/admin/authentication");
        driver.findElement(By.xpath("//input[@type =\"email\"]")).sendKeys("admin@example.com");
        driver.findElement(By.xpath("//input[@type =\"password\"]")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();
        Thread.sleep(2000);

        //navigate to a page
        driver.navigate().to("http://anhtester.com");


        // navigate to previous page
        driver.navigate().back();
        Thread.sleep(3000);

        // refresh a page
        driver.navigate().refresh();
        Thread.sleep(4000);

        // navigate to next page
        driver.navigate().forward();

        driver.quit();



    }
}
