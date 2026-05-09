package com.linh.checkbox_radio_dropdown;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class HandleCheckbox {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://crm.anhtester.com/admin");
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("admin@example.com");
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//span[normalize-space()='Tasks']")).click();
        driver.findElement(By.xpath("//a[normalize-space()='New Task']")).click();
        Thread.sleep(3000);

        Boolean checkboxPublic = driver.findElement(By.xpath("//input[@id='task_is_public']")).isSelected();
        Boolean checkboxBillable= driver.findElement(By.xpath("//input[@id='task_is_billable']")).isSelected();
        driver.findElement(By.xpath("//input[@id='task_is_billable']")).click();
        System.out.println("checkbox Public is selected: "+checkboxPublic);
        System.out.println("checkbox Billable is selected: "+checkboxBillable);

        List<WebElement> multipleCheckBox = driver.findElements(By.xpath(" (//form[@id ='task-form']//div[contains(@class, 'task-add-edit')])//input"));
        System.out.println("total multiple checkboxs: "+multipleCheckBox.size());
        for(int i =0; i< multipleCheckBox.size();i++){
            multipleCheckBox.get(i).click();
            System.out.println("Selected: "+(i+1));
        }

        //Neu muon 1 thanh cu the thi xu ly rieng
        Thread.sleep(2000);
        driver.findElement(By.xpath(" ((//form[@id ='task-form']//div[contains(@class, 'task-add-edit')])//input)[1]")).click();


        Thread.sleep(2000);
        for(int i =0; i< multipleCheckBox.size();i++){
            System.out.println(multipleCheckBox.get(i).isSelected());
            if (multipleCheckBox.get(i).isSelected()==false){
                System.out.println("Fail. All checkbox are not selected.");
                break;
            }
        }

        Thread.sleep(2000);
        driver.close();
        driver.quit();
    }
}
