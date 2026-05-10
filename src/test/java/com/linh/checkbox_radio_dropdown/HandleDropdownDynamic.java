package com.linh.checkbox_radio_dropdown;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class HandleDropdownDynamic {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        driver.get("https://techydevs.com/demos/themes/html/listhub-demo/listhub/index.html");
        Thread.sleep(10000);

        //Click vào dropdown Category
        driver.findElement(By.xpath("//span[text() = 'Select a Category']")).click();

        //Search giá trị cần chọn
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[text()='Select a Category']/parent::a/following-sibling::div//input")).sendKeys("Travel");
        //Click chọn Text đã search (cần chọn)
        Thread.sleep(1000);
        driver.findElement(By.xpath("//li[@class='active-result highlighted']")).click();

        //Cách ENTER dùng cho trường hợp chỉ hiển thị 1 giá trị lựa chọn
        //Actions action = new Actions(driver);
        //action.sendKeys(Keys.ENTER).perform();

        Thread.sleep(2000);
        driver.close();
        driver.quit();
    }
}
