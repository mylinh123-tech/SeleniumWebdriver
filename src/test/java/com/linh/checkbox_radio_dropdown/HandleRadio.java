package com.linh.checkbox_radio_dropdown;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class HandleRadio {
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

        driver.findElement(By.xpath("//span[@class='menu-text'][normalize-space()='Sales']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//span[normalize-space()='Proposals']")).click();
        driver.findElement(By.xpath("//a[normalize-space()='New Proposal']")).click();

        // Scroll to end page
        Thread.sleep(2000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");

        boolean radioHours = driver.findElement(By.xpath("//label[normalize-space()='Hours']/preceding-sibling::input")).isSelected();
        System.out.println("Check radio is selected:" + radioHours);

        driver.findElement(By.xpath("//label[normalize-space()='Hours']")).click();

        boolean radioHours1 = driver.findElement(By.xpath("//label[normalize-space()='Hours']/preceding-sibling::input")).isSelected();
        System.out.println("Check radio is selected:" + radioHours1);

        List<WebElement>listRadio = driver.findElements(By.xpath("//input[@name = 'show_quantity_as']"));
        int count = 0;
        for (int i = 0; i< listRadio.size() ; i++)
        {
            System.out.println("Selected: "+listRadio.get(i).isSelected());

            if (listRadio.get(i).isSelected() == true){
                count = count + 1;
            }
        }

        if (count != 1){
            System.out.println("Fail, Should 1 radio is selected");
        }

        Thread.sleep(2000);
        driver.close();
        driver.quit();
    }
}
