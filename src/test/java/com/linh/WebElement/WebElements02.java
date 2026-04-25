package com.linh.WebElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class WebElements02 {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://angular-reactive-forms-zvzqvd.stackblitz.io/");
        driver.findElement(By.xpath("//span[normalize-space()='Run this project']")).click();
        boolean checkButtonSubmit = driver.findElement(By.xpath("//button[normalize-space()='Submit']")).isEnabled();
        System.out.println("checkButtonSubmit: " + checkButtonSubmit);
        boolean checkButtonHacked = driver.findElement(By.xpath("//button[normalize-space()='\"Hacked\" Submit button']")).isEnabled();
        System.out.println("checkButtonHacked: " + checkButtonHacked);

        driver.findElement(By.id("email")).sendKeys("admin@example.com");
        driver.findElement(By.id("password")).sendKeys("123456");

        boolean checkButtonSubmit2 = driver.findElement(By.xpath("//button[normalize-space()='Submit']")).isEnabled();
        System.out.println("checkButtonSubmit: " + checkButtonSubmit2);
        boolean checkButtonHacked2 = driver.findElement(By.xpath("//button[normalize-space()='\"Hacked\" Submit button']")).isEnabled();
        System.out.println("checkButtonHacked: " + checkButtonHacked2);


        driver.quit();
    }
}
