package com.linh.Bai15_Waits;

import com.linh.common.BaseTest;
import com.linh.keywords.ActionKeyword;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

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

        ActionKeyword.setText(driver,By.id("iusername"),"admin_example",3);
        ActionKeyword.setText(driver,By.xpath("//input[@id='ipassword']"),"123456",3);
        ActionKeyword.clickElement(driver,By.xpath("//span[normalize-space()='Login']"),5);

        //Kiem tra element xuat hien hay khong
        boolean isElementPresent = ActionKeyword.isElementPresent(driver, By.xpath("//h6[normalize-space()='Welcome Admin Example']"),3);
        Assert.assertTrue(isElementPresent, "Login Fail");



    }

}
