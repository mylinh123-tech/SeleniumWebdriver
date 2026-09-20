package com.linh.Bai26_Parameters_MultiBrowser.pages;

import com.linh.constants.ConfigData;
import com.linh.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;


public class DashBoardPage extends BasePage {
    private String dashboardPageUrl= "/admin/";
    private By menuDashboard = By.xpath("//span[normalize-space()='Dashboard']");
    private By totalInvoicesAwaitingPayment = By.xpath("//span[normalize-space()='Invoices Awaiting Payment']/parent::div/following-sibling::span");
    private By totalConvertedLeads = By.xpath("//span[normalize-space()='Converted Leads']/parent::div/following-sibling::span");
    private By totalProjectsInprogress = By.xpath("//span[normalize-space()='Projects In Progress']/parent::div/following-sibling::span");
    private By totalTasksNotFinished = By.xpath(" //span[normalize-space()='Tasks Not Finished']/parent::div/following-sibling::span");

    public DashBoardPage( WebDriver driver) {
        super(driver);
        new WebUI(driver);
    }
    public boolean isDashBoardPageOpen(){
        WebUI.waitForPageLoaded();
        return WebUI.isElementVisible(menuDashboard);
    }

    public void verifyNavigateToDashBoardPage(){
        WebUI.waitForPageLoaded();
        WebUI.waitForElementVisible(menuDashboard, 20);
        Assert.assertEquals(WebUI.getCurrentURL(), ConfigData.Base_URL + dashboardPageUrl,"Dashboard URL was not loaded");
    };

    public void verifyTotalInvoicesAwaitingPayment (String expectedValue){
        WebUI.waitForPageLoaded();
        Assert.assertTrue(WebUI.checkElementExist(totalInvoicesAwaitingPayment), "The section Invoices Awaiting Payment not display.");
        Assert.assertEquals(WebUI.getElementText(totalInvoicesAwaitingPayment), expectedValue, "FAIL!! Invoices Awaiting Payment total not match.");

    };
    public void verifyTotalConvertedLeads(String expectedValue){
        WebUI.waitForPageLoaded();
        Assert.assertTrue(WebUI.checkElementExist(totalConvertedLeads), "The section Converted Leads not display.");
        Assert.assertEquals(WebUI.getElementText(totalConvertedLeads), expectedValue, "FAIL!! Converted Leads total not match.");

    };

    public void verifyTotalProjectsInprogress (int projectsInprogress, int projectTotal){
        WebUI.waitForPageLoaded();
        WebUI.waitForElementVisible(totalProjectsInprogress, 20);
        String text = WebUI.getElementText(totalProjectsInprogress);

        Assert.assertEquals(projectsInprogress +" / "+projectTotal, text, "Total Projects In Progress is not match");

    };
    public void verifyTotalTasksNotFinished(String expectedValue){
        WebUI.waitForPageLoaded();
        Assert.assertTrue(WebUI.checkElementExist(totalTasksNotFinished), "The section Tasks Not Finished not display.");
        Assert.assertEquals(WebUI.getElementText(totalTasksNotFinished), expectedValue, "FAIL!! Tasks Not Finished total not match.");

    };


}
