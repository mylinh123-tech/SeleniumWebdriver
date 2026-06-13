package com.linh.Bai13_Alert_WindowPopup_IFrame;
import com.linh.common.BaseTest;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Driver;

public class HandleAlert extends BaseTest {
    @Test
    public void demoHandleAlertAccept() throws InterruptedException {
        driver.get("https://www.lambdatest.com/selenium-playground/javascript-alert-box-demo");
        Thread.sleep(2000);

        //Click vào nút "Click Me" thứ nhất
        driver.findElement(By.xpath("(//button[text()='Click Me'])[1]")).click();
        Thread.sleep(1000);

        //Khởi tạo class Alert
        Alert alert1 = driver.switchTo().alert();
        //Dùng hàm accept() để xác nhận Alert (tương ứng click vào nút OK)

        Assert.assertEquals(alert1.getText(),
                "I am an alert box!",
                "Content is incorrect");

       //or  driver.switchTo().alert().accept();

        Thread.sleep(1000);
        alert1.accept();
    }

    @Test
    public void demoHandleAlertDismiss() throws InterruptedException {
        driver.get("https://www.lambdatest.com/selenium-playground/javascript-alert-box-demo");
        Thread.sleep(2000);

        //Click vào nút "Click Me" thứ hai
        driver.findElement(By.xpath("(//button[text()='Click Me'])[2]")).click();
        Thread.sleep(1000);

        //Khởi tạo class Alert
        Alert alert2 = driver.switchTo().alert();
        //Dùng hàm dismiss() để từ chối Alert (tương ứng click vào nút Cancel)
        alert2.dismiss();
        Assert.assertEquals(driver.findElement(By.id("confirm-demo")).getText(),"You pressed Cancel!");
        Thread.sleep(1000);

        driver.findElement(By.xpath("(//button[text()='Click Me'])[2]")).click();
        Thread.sleep(1000);
        Alert alert3 = driver.switchTo().alert();
        alert3.accept();
        Assert.assertEquals(driver.findElement(By.id("confirm-demo")).getText(),"You pressed OK!");
        Thread.sleep(1000);
    }

    @Test
    public void demoHandleAlertInputText() throws InterruptedException {
        driver.get("https://www.lambdatest.com/selenium-playground/javascript-alert-box-demo");
        Thread.sleep(2000);

        //Mở Alert Input text, click nút Click Me thứ ba
        driver.findElement(By.xpath("(//button[text()='Click Me'])[3]")).click();
        Thread.sleep(1000);

        //Khởi tạo class Alert
        Alert alert3 = driver.switchTo().alert();
        alert3.sendKeys("Anh Tester Demo Alert");
        alert3.accept();

        Thread.sleep(1000);
        Assert.assertEquals(driver.findElement(By.xpath(
                "//p[@id='prompt-demo']")).getText(),
                "You have entered 'Anh Tester Demo Alert' !",
                "Chưa điền được text");

        Thread.sleep(1000);
    }
}
