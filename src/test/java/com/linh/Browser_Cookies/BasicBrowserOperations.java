package com.linh.Browser_Cookies;

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

        // Get the title of the page
        String title = driver.getTitle();
        System.out.println("Page title: " + title);

        // Get the current URL
        String url = driver.getCurrentUrl();
        System.out.println("Page URL: " + url);

        // Get the current page HTML source
        String html = driver.getPageSource();
        System.out.println("Page HTML: " + html);

        System.out.println(html.contains("<button type=\"button\""));


        driver.quit();





    }
}
