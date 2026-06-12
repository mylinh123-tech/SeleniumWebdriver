package com.linh.Bai11_Assert;

import com.linh.common.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DemoHardAssert extends BaseTest {
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

    }


}
