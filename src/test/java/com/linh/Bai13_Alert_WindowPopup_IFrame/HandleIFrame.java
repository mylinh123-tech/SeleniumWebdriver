package com.linh.Bai13_Alert_WindowPopup_IFrame;

import com.linh.common.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class HandleIFrame extends BaseTest {

        @Test
        public void demoHandleIFrame() throws InterruptedException {

            driver.navigate().to("https://www.lambdatest.com/selenium-playground/iframe-demo/");
            sleep(3);
            System.out.println("IFrame total: " + driver.findElements(By.tagName("iframe")).size());

            //----Switch to content of iframe (Editor)--------
            //driver.switchTo().frame(0); //Thẻ iframe thứ nhất lấy theo vị trí
            driver.switchTo().frame("iFrame1");
            sleep(1);
            System.out.println(driver.findElement(By.xpath("//div[@class='rsw-ce']")).getText());
            driver.findElement(By.xpath("//div[@class='rsw-ce']")).click();

            Actions action = new Actions(driver);
            action.keyDown(Keys.COMMAND).sendKeys("a").keyDown(Keys.COMMAND).build().perform();
            sleep(5);
           // action.click(driver.findElement(By.xpath("//button[@title ='Bold']")));
            driver.findElement(By.xpath("//button[@title ='Bold']")).click();
            sleep(5);


            //1. Switch to Parent WindowHandle
            driver.switchTo().parentFrame(); //Chuyển về nội dung chính không thuộc iframe nào
            sleep(2);
            //2. Switch to iframe icon of Messenger
            driver.switchTo().frame(1); //Thẻ iframe thứ hai
            driver.findElement(By.xpath("//a[normalize-space()='API Reference']")).click(); //Nhấn icon để ẩn messenger chat đi
            sleep(2);
        }




}
