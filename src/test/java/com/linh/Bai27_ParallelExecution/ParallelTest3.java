package com.linh.Bai27_ParallelExecution;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import java.time.Duration;

public class ParallelTest3 {

    @Test
    public void FireBox3() throws InterruptedException {
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://anhtester.com");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[@href='https://anhtester.com/tools']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[contains(text(),'Chứng chỉ')]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[contains(text(),'Khai giảng')]")).click();
        driver.quit();
    }

    @Test
    public void EdgeTest3() throws InterruptedException {
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://anhtester.com");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[contains(text(),'Chứng chỉ')]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[@href='https://anhtester.com/tools']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[contains(text(),'Khai giảng')]")).click();
        driver.quit();
    }

    @Test
    public void ChromeTest3() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://anhtester.com");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[contains(text(),'Khai giảng')]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[contains(text(),'Chứng chỉ')]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[@href='https://anhtester.com/tools']")).click();

        driver.quit();
    }
}
