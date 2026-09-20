package com.linh.Bai22_23_ThucHanhPOM.pages;

import com.linh.constants.ConfigData;
import com.linh.keywords.ActionKeyword;
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
    private By menuDashboard = By.xpath("//span[normalize-space()='Dashboard']");
    private By totalInvoicesAwaitingPayment = By.xpath("//span[normalize-space()='Invoices Awaiting Payment']/parent::div/following-sibling::span");
    private By totalConvertedLeads = By.xpath("//span[normalize-space()='Converted Leads']/parent::div/following-sibling::span");
    private By totalProjectsInprogress = By.xpath("//span[normalize-space()='Projects In Progress']/parent::div/following-sibling::span");
    private By totalTasksNotFinished = By.xpath(" //span[normalize-space()='Tasks Not Finished']/parent::div/following-sibling::span");

    public DashBoardPage( WebDriver driver) {
        super(driver);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    public boolean isDashBoardPageOpen(){
        ActionKeyword.waitForPageLoaded(driver);
        return driver.findElement(menuDashboard).isDisplayed();
    }

    public void verifyNavigateToDashBoardPage(){
        ActionKeyword.waitForPageLoaded(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(menuDashboard));
        Assert.assertEquals(driver.getCurrentUrl(), ConfigData.Base_URL + dashboardPageUrl,"Dashboard URL was not loaded");
    };

    public void verifyTotalInvoicesAwaitingPayment (String expectedValue){
        ActionKeyword.waitForPageLoaded(driver);
        Assert.assertTrue(ActionKeyword.isElementPresent(driver, totalInvoicesAwaitingPayment), "The section Invoices Awaiting Payment not display.");
        Assert.assertEquals(driver.findElement(totalInvoicesAwaitingPayment).getText(), expectedValue, "FAIL!! Invoices Awaiting Payment total not match.");

    };
    public void verifyTotalConvertedLeads(String expectedValue){
        ActionKeyword.waitForPageLoaded(driver);
        Assert.assertTrue(ActionKeyword.isElementPresent(driver, totalConvertedLeads), "The section Converted Leads not display.");
        Assert.assertEquals(driver.findElement(totalConvertedLeads).getText(), expectedValue, "FAIL!! Converted Leads total not match.");

    };

    public void verifyTotalProjectsInprogress (int projectsInprogress, int projectTotal){
        ActionKeyword.waitForPageLoaded(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(totalProjectsInprogress));
        String text = driver.findElement(totalProjectsInprogress).getText();
        System.out.println("Total Projects In Progress: "+text);
        Assert.assertEquals(projectsInprogress +" / "+projectTotal, text, "Total Projects In Progress is not match");

    };
    public void verifyTotalTasksNotFinished(String expectedValue){
        ActionKeyword.waitForPageLoaded(driver);
        Assert.assertTrue(ActionKeyword.isElementPresent(driver, totalTasksNotFinished), "The section Tasks Not Finished not display.");
        Assert.assertEquals(driver.findElement(totalTasksNotFinished).getText(), expectedValue, "FAIL!! Tasks Not Finished total not match.");

    };


}
