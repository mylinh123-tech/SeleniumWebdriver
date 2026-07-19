package com.linh.Bai17PageObjectModel.pages;

import com.linh.constants.ConfigData;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class DashBoardPage extends BasePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private String dashboardPageUrl= "/admin/";
    private By totalInvoicesAwaitingPayment = By.xpath("//span[normalize-space()='Invoices Awaiting Payment']/parent::div/following-sibling::span");
    private By totalConvertedLeads = By.xpath("//span[normalize-space()='Converted Leads']/parent::div/following-sibling::span");
    private By totalProjectsInprogress = By.xpath("//span[normalize-space()='Projects In Progress']/parent::div/following-sibling::span");
    private By totalTasksNotFinished = By.xpath(" //span[normalize-space()='Tasks Not Finished']/parent::div/following-sibling::span");

    public DashBoardPage( WebDriver driver) {
        super(driver);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    public void verifyNavigateToDashBoardPage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(menuDashboard));
        Assert.assertEquals(driver.getCurrentUrl(), ConfigData.Base_URL + dashboardPageUrl,"Dashboard URL was not loaded");
    };

    public void verifyTotalInvoicesAwaitingPayment (){

    };
    public void verifyTotalConvertedLeads(){};

    public void verifyTotalProjectsInprogress (int projectsInprogress, int projectTotal){
        wait.until(ExpectedConditions.visibilityOfElementLocated(totalProjectsInprogress));
        String text = driver.findElement(totalProjectsInprogress).getText();
        System.out.println("Total Projects In Progress: "+text);
        Assert.assertEquals(projectsInprogress +" / "+projectTotal, text, "Total Projects In Progress is not match");

    };
    public void verifyTotalTasksNotFinished(){};


}
