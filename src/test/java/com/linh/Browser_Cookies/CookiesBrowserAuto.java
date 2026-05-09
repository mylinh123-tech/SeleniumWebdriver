package com.linh.Browser_Cookies;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.Set;

public class CookiesBrowserAuto {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://crm.anhtester.com/admin/authentication");
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("admin@example.com");
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
        Thread.sleep(2000);

        //Get Cookie by Name
        Cookie cookie = driver.manage().getCookieNamed("sp_session");
        System.out.println(cookie);




        System.out.println("Cookie value: " + cookie.getValue());
        System.out.println(cookie.isHttpOnly());
        System.out.println(cookie.getExpiry());

        //Lưu cookie value vào file trung gian (JSON, Properties, .ENV)

//        //Convert Date to GMT+7 (Giờ Việt Nam)
//        String cookieText = cookie.toString();
//
//        String expiryPart = cookieText.split("expires=")[1]
//                .split(" GMT")[0];
//
//        String rawDate = expiryPart.substring(expiryPart.indexOf(",") + 2);
//
//        System.out.println(rawDate);
//
//        // Format input
//        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(
//                "dd 'thg' M yyyy HH:mm:ss",
//                new Locale("vi", "VN")
//        );
//
//        // Parse sang LocalDateTime
//        LocalDateTime dateTime = LocalDateTime.parse(rawDate, inputFormatter);
//
//        // Format output
//        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(
//                "dd/MM/yyyy HH:mm:ss"
//        );
//
//        String formatted = dateTime.format(outputFormatter);
//
//        System.out.println(formatted);

        // Get all cookies
        Set<Cookie> cookies = driver.manage().getCookies();
        System.out.println("Total cookies: " + cookies.size());
        for (Cookie cookie1 : cookies) {
            System.out.println(cookie1.getName() + " = " + cookie1.getValue());
        }

        // Delete a cookie by name
        //driver.manage().deleteCookieNamed("CookieName");

        // Delete all cookies
        //driver.manage().deleteAllCookies();

        Thread.sleep(2000);
        driver.quit(); //Tắt thoát trình duyệt

    }
}
