package com.linh.Bai14_JavaScriptExecutor;

import com.linh.common.BaseTest;
import com.linh.keywords.WebUI;
import com.linh.utils.LocalStorageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Driver;

public class DemoJSExecutor extends BaseTest {
    @Test
    public void TestDemo01() {
        driver.get("https://cms.anhtester.com/login");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('value','admin@example.com');", driver.findElement(By.xpath(" //input[@id ='email']")));
        js.executeScript("arguments[0].setAttribute('value','123456');", driver.findElement(By.xpath("//input[@id ='password']")));
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[@type = 'submit']")));
        try {
            driver.findElement(By.xpath("//span[normalize-space()='Dashboard']"));
            Assert.assertTrue(true);
        } catch (Exception e){
            Assert.fail("Failed to login");

        }
        sleep(2);
        //Để lấy nội dung của toàn bộ trang web trong Selenium
        String innerText = js.executeScript("return document.documentElement.innerText;").toString();
        System.out.println(innerText);

        js.executeScript("return window.innerHeight;").toString();
        js.executeScript("return window.innerWidth;").toString();

    }
    @Test
    public void TestDemo02(){
        driver.get("https://anhtester.com");
        JavascriptExecutor js = (JavascriptExecutor) driver;
       // Để cuộn trang theo chiều dọc 500px
        js.executeScript("window.scrollTo(0,500)");
        sleep(3);
        js.executeScript("window.scrollTo(0,1000)");
        sleep(3);
        js.executeScript("window.scrollTo(0,1500)");
        sleep(3);
        // Để lấy Chiều cao và Chiều rộng của một trang web
        System.out.println(js.executeScript("return window.innerHeight;").toString());
        System.out.println( js.executeScript("return window.innerWidth;").toString());

        //Để cuộn trang theo chiều dọc cho đến hết (cuối trang)
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
        sleep(3);

    }
    @Test
    public void TestDemo03(){
        driver.get("https://anhtester.com");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Để cuộn tới 1 phần tử trong trang (này dùng nhiều)
        //Giá trị true là cuộn nằm phía trên
        //Giá trị false là cuộn nằm phía dưới
        js.executeScript("arguments[0].scrollIntoView(false);",driver.findElement(By.xpath(" //h2[normalize-space() ='Blog Testing']")));
        sleep(5);

    }
    @Test
    public void TestDemo04(){
 //       driver.get("https://anhtester.com");
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//
//        //Set giá trị vào LocalStorage trên Browser
//        js.executeScript("window.localStorage.setItem(arguments[0],arguments[1])","email","vothaian@example.com");
//        driver.findElement(By.xpath("//a[@id='btn-login']")).click();
//
//
//        // Get giá trị luu tren LocalStorage vao bien emailFromLocalStorage
//        String emailFromLocalStorage = (String) js.executeScript("return window.localStorage.getItem(arguments[0])", "email");
//        driver.findElement(By.xpath(" //input[@name ='email']")).sendKeys(emailFromLocalStorage);
//        sleep(5);

        driver.get("https://anhtester.com");
        LocalStorageUtils.setValueToLocalStorage(driver, "email","nanobitaxuka@gmail.com" );
        driver.findElement(By.xpath("//a[@id='btn-login']")).click();
        sleep(1);
        String emailFromLocalStorage = LocalStorageUtils.getValueToLocalStorage(driver, "email");
        driver.findElement(By.xpath(" //input[@name ='email']")).sendKeys(emailFromLocalStorage);
        sleep(5);
    }

    @Test
    public void TestDemo05(){
        driver.get("https://cms.anhtester.com/login");
        JavascriptExecutor js = (JavascriptExecutor)driver;
        //Highlight phần tử trên web
//        js.executeScript("arguments[0].style.border='3px solid red'", driver.findElement(By.id("email")));
//        driver.findElement(By.id("email")).sendKeys("admin@example.com");
//        js.executeScript("arguments[0].style.border='3px solid red'", driver.findElement(By.id("password")));
//        driver.findElement(By.id("password")).sendKeys("123456");
//        driver.findElement(By.xpath("//button[@type = 'submit']")).click();

        WebUI.setText(driver, By.id("email"), "admin@example.com");
        sleep(1);
        WebUI.setText(driver, By.id("password"), "123456");
        sleep(1);
        WebUI.clickElement(driver, By.xpath("//button[@type = 'submit']"));
        sleep(2);

    }


}
