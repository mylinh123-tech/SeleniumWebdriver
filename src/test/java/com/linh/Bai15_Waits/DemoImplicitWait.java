package com.linh.Bai15_Waits;

import com.linh.common.BaseTest;
import com.linh.keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class DemoImplicitWait extends BaseTest {
    @Test
  public void testImplicitWait (){

        //Co che wait chi ap dung cho element -> ko ap dung cho alert,..
        driver.get("https://hrm.anhtester.com/erp/login");//Cho theo basetest
        driver.findElement(By.id("iusername")).sendKeys("admin_example");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //Bat dau tu day cho 10s
        driver.findElement(By.xpath("//input[@id='ipassword']")).sendKeys("123456");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); //Bat dau tu day cho 5s //Neu de 0s -> reset co che wait
        driver.findElement(By.xpath("//span[normalize-space()='Login']")).click();
        //Kiem tra element co xuat hien khong
        boolean isElementPresent = WebUI.isElementPresent(driver, By.xpath("//h6[normalize-space()='Welcome Admin Example']"));
        Assert.assertTrue(isElementPresent, "Login Fail");



    }

}
