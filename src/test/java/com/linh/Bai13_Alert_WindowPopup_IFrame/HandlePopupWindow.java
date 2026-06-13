package com.linh.Bai13_Alert_WindowPopup_IFrame;

import com.linh.common.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WindowType;
import org.testng.annotations.Test;

import java.util.Set;

public class HandlePopupWindow extends BaseTest {
    @Test
    public  void testOpenNewTab(){
        driver.get("https://anhtester.com");
        sleep(2);
        driver.switchTo().newWindow(WindowType.TAB);
        sleep(2);
        driver.get("https://google.com");
    }

    @Test
    public  void testOpenNewWindow(){
        driver.get("https://anhtester.com");
        sleep(2);
        driver.switchTo().newWindow(WindowType.WINDOW);
        sleep(2);
        driver.get("https://www.facebook.com/linh.linhlem.3154");
        sleep(3);
    }

    @Test
    public void demoNotSwitchToTab() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@id='tabButton']")).click();
        Thread.sleep(1000);
        //Sau khi chuyển hướng sang Tab mới thì getText cái header
        System.out.println(driver.findElement(By.xpath("//h1[@id='sampleHeading']")).getText());
        Thread.sleep(1000);
    }
    @Test
    public void demoSwitchToTab() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
        Thread.sleep(3000);

        driver.findElement(By.xpath("//button[@id='tabButton']")).click();
        sleep(2);

        Set<String> windows = driver.getWindowHandles();
        String firstWindow = (String)windows.toArray()[0]; //Cửa sổ đầu
        String secondWindow = (String)windows.toArray()[1]; //Cửa sổ thứ hai

        driver.switchTo().window(secondWindow);
        //Sau khi chuyển hướng sang Tab mới thì getText cái header
        System.out.println(driver.findElement(By.xpath("//h1[@id='sampleHeading']")).getText());
        sleep(2);
        driver.close();// close current tab (tab 2)
        // Chuyen ve cua so thu nhat
        driver.switchTo().window(firstWindow);
        sleep(2);
        System.out.println(driver.findElement(By.xpath("//h1[normalize-space()='Browser Windows']")));



    }

}
