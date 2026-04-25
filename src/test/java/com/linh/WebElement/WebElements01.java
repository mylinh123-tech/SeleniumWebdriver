package com.linh.WebElement;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class WebElements01 {
    public static boolean isElementVisible (WebDriver driver, By by) {
        try {
            return driver.findElement(by).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://crm.anhtester.com/admin/authentication");
        driver.findElement(By.xpath("//input[@type =\"email\"]")).sendKeys("admin@example.com");
        driver.findElement(By.xpath("//input[@type =\"password\"]")).sendKeys("123456");
        Thread.sleep(2000);

        //driver.findElement(By.xpath("//input[@type =\"email\"]")).clear();
        // driver.findElement(By.xpath("//input[@type =\"password\"]")).clear();

        // Click Remember me
        driver.findElement(By.xpath("//label[normalize-space() ='Remember me']")).click();
        boolean checkSelectedRememberMe = driver.findElement(By.xpath("//input[@id ='remember']")).isSelected();
        System.out.println("checkSelectedRememberMe: "+ checkSelectedRememberMe);


        boolean checkButtonLogin = driver.findElement(By.xpath("//button[normalize-space() = 'Login']")).isDisplayed();
        System.out.println("checkButtonLogin: "+ checkButtonLogin);

        driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();
        Thread.sleep(3000);
        boolean checkEmailErrorMessage= isElementVisible(driver,By.xpath("//div[normalize-space() ='The Email Address field is required.']"));
        boolean checkEmailErrorPassword = isElementVisible(driver,By.xpath("//div[normalize-space() ='The Password field is required.']"));

        //boolean checkEmailErrorMessage = driver.findElement(By.xpath("//div[normalize-space() ='The Email Address field is required.']")).isDisplayed();
        //boolean checkEmailErrorPassword= driver.findElement(By.xpath("//div[normalize-space() ='The Password field is required.']")).isDisplayed();
        System.out.println("checkEmailErrorMessage: "+ checkEmailErrorMessage);
        System.out.println("checkEmailErrorPassword: "+ checkEmailErrorPassword);

        driver.quit();

    }
}
