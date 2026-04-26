package com.linh.WebElement;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;


public class WebElements03 {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://crm.anhtester.com/admin/authentication");
        driver.findElement(By.xpath("//input[@type =\"email\"]")).sendKeys("admin@example.com");
        driver.findElement(By.xpath("//input[@type =\"password\"]")).sendKeys("123456");
        Thread.sleep(2000);
        WebElement loginButton = driver.findElement(By.xpath("//button[normalize-space() = 'Login']"));
        System.out.println(loginButton.getCssValue("background-color"));
        System.out.println(loginButton.getCssValue("font-size"));
        System.out.println(loginButton.getCssValue("font-weight"));

        Dimension demension = loginButton.getSize();
        System.out.println("height"+demension.getHeight() + ", width: "+demension.getWidth());

        Point point = loginButton.getLocation();
        System.out.println("x: "+point.x + ", y: "+point.y);

       // driver.findElement(By.xpath("//input[@type =\"password\"]")).submit();
        driver.findElement(By.xpath("//input[@type =\"password\"]")).sendKeys(Keys.ENTER);

        Thread.sleep(3000);


        String InvoiceAwaitingPaymentTotal = driver.findElement(By.xpath("//div[normalize-space()='Invoices Awaiting Payment']/following-sibling::span")).getText();
        System.out.println(" InvoiceAwaitingPaymentTotal: "+  InvoiceAwaitingPaymentTotal);
        Thread.sleep(2000);

        driver.findElement(By.xpath("//a[@href =\"https://crm.anhtester.com/admin/clients\"]")).click();
        System.out.println(driver.findElement(By.xpath("//table[@id = 'clients']//tbody//tr[1]//td[3]//a")).getText());
        Thread.sleep(2000);


        driver.findElement(By.xpath("//table[@id = 'clients']//tbody//tr[1]//td[3]//a")).click();
        Thread.sleep(2000);

        //Get Attribute

        System.out.println(driver.findElement(By.xpath("//input[@id='company']")).getAttribute("value"));
        driver.close();
        driver.quit();

    }
}
