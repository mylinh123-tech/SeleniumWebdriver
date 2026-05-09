package com.linh.checkbox_radio_dropdown;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

public class HandleDropdownStatic {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://crm.anhtester.com/admin");
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("admin@example.com");
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//span[normalize-space()='Customers']")).click();

        WebElement selectElement= driver.findElement(By.xpath("//select[@name='clients_length']"));

        Select select = new Select(selectElement);
        //select option contain value = 10
        select.selectByVisibleText("10");
        Thread.sleep(2000);

        select.selectByVisibleText("All");

        // print option is select
        System.out.println(select.getFirstSelectedOption().getText());

        System.out.println("Check multiple select: " + select.isMultiple());

        Thread.sleep(2000);
        driver.close();
        driver.quit();
    }
}
