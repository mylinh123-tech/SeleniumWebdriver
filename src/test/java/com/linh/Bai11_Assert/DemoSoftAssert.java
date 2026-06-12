package com.linh.Bai11_Assert;

import com.linh.common.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class DemoSoftAssert extends BaseTest {
    public SoftAssert softAssert;
    @BeforeClass
    public void SetUp(){
        softAssert = new SoftAssert();
    }

    @AfterClass
    public void AfterSetUp(){
        softAssert.assertAll();
    }

    @Test
    public void testHardAssert() throws InterruptedException {
        driver.get("https://crm.anhtester.com/admin/authentication");
        driver.findElement(By.xpath("//input[@type =\"email\"]")).sendKeys("admin@example.com");
        driver.findElement(By.xpath("//input[@type =\"password\"]")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();
        Thread.sleep(3000);
        Assert.assertEquals(driver.getCurrentUrl(),"https://crm.anhtester.com/admin/", "URL invalid after logging in");
        try{
            driver.findElement(By.xpath(" //li[contains(@class, 'icon header-user-profile')]")).isDisplayed();

        }catch (Exception e)
        {
            Assert.fail("Menu Profile is not displayed. Exception"+e.getMessage());
        }

        driver.findElement(By.xpath("//span[normalize-space()='Customers']")).click();
        String header =  driver.findElement(By.xpath("//span[normalize-space()='Customers Summary']")).toString();
        softAssert.assertEquals(header,"Customers Summary 123","Header does not match with design" );

        try{
            driver.findElement(By.xpath("//span[normalize-space()='Customers Summary']")).isDisplayed();

        }catch (Exception e)
        {
            softAssert.fail("Header is not displayed "+e.getMessage());
        }


    }



}
