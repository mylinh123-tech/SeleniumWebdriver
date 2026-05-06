package com.linh.WebElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class FindElements_ListWebElement {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.get("https://crm.anhtester.com/admin/authentication");
        driver.findElement(By.xpath("//input[@type =\"email\"]")).sendKeys("admin@example.com");
        driver.findElement(By.xpath("//input[@type =\"password\"]")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();
        Thread.sleep(2000);

        List <WebElement> MenuList = driver.findElements(By.xpath("//ul[@id = 'side-menu']/li[contains(@class, 'menu-item')]")) ;
        for (WebElement menu:MenuList){
            System.out.println(menu.getText());

        }
        Thread.sleep(2000);
        driver.findElement(By.xpath("//span[@class='menu-text' and normalize-space() = 'Sales']")).click();

        Thread.sleep(2000);
        List<WebElement> SubMenuList = driver.findElements(By.xpath("(//span[@class='menu-text' and normalize-space() = 'Sales']/ancestor::a)/following-sibling::ul/li//span"));
        System.out.println("----------------------------------------");
        for (WebElement submenu:SubMenuList){
            System.out.println(submenu.getText());
        }

        driver.close();
        driver.quit();

    }


}
