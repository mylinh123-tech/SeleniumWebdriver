package com.linh.Bai15_Waits;

import com.linh.common.BaseTest;
import com.linh.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class DemoExplicitWait extends BaseTest {
    @Test
  public void testExplicitWait (){

        //Explicit ap dung cho 1 element/ 1 dieu kien cu the->  ap dung cho element, alert,..

        driver.get("https://hrm.anhtester.com/erp/login");//Cho theo basetest

       /* WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("iusername")));
        driver.findElement(By.id("iusername")).sendKeys("admin_example");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='ipassword']")));
        driver.findElement(By.xpath("//input[@id='ipassword']")).sendKeys("123456");

        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(7));
        wait2.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[normalize-space()='Login']")));
        driver.findElement(By.xpath("//span[normalize-space()='Login']")).click();*/

        WebUI.setText(driver,By.id("iusername"),"admin_example",3);
        WebUI.setText(driver,By.xpath("//input[@id='ipassword']"),"123456",3);
        WebUI.clickElement(driver,By.xpath("//span[normalize-space()='Login']"),5);

        //Kiem tra element xuat hien hay khong
        boolean isElementPresent = WebUI.isElementPresent(driver, By.xpath("//h6[normalize-space()='Welcome Admin Example']"),3);
        Assert.assertTrue(isElementPresent, "Login Fail");



    }

}
