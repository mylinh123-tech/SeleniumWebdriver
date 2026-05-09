package com.linh.Browser_Cookies;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class CookiesBrowser {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://crm.anhtester.com/admin/authentication");
        //add new cookie
        Cookie newCookie  = new Cookie("sp_session", "0f2b684d7ef67f3624b5b609553d09cd7ef5d386");
        driver.manage().addCookie(newCookie);
        Thread.sleep(2000);
        driver.get("https://crm.anhtester.com/admin/authentication");
        Thread.sleep(5000);
        driver.quit(); //Tắt thoát trình duyệt



    }
}
