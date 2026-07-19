package com.linh.Bai20_PageFactory.pages;

import com.linh.constants.ConfigData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DashBoardPage extends BasePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private String dashboardPageUrl= "/admin/";

    @FindBy(xpath = "//span[normalize-space()='Invoices Awaiting Payment']/parent::div/following-sibling::span")
    private WebElement totalInvoicesAwaitingPayment;

    @FindBy(xpath = "//span[normalize-space()='Converted Leads']/parent::div/following-sibling::span")
    private WebElement totalConvertedLeads;

    @FindBy(xpath = "//span[normalize-space()='Projects In Progress']/parent::div/following-sibling::span")
    private WebElement totalProjectsInprogress;

    @FindBy(xpath = "//span[normalize-space()='Tasks Not Finished']/parent::div/following-sibling::span")
    private WebElement totalTasksNotFinished;

    @FindBys({
            @FindBy(xpath = "//ul[@id='side-menu']/li[contains(@class,'menu-item')]/a/span[contains(@class,'menu-text')]")
    })
    private List<WebElement> menuList;

    public DashBoardPage( WebDriver driver) {
        super(driver);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }
    public void verifyNavigateToDashBoardPage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(menuDashboard));
        Assert.assertEquals(driver.getCurrentUrl(), ConfigData.Base_URL + dashboardPageUrl,"Dashboard URL was not loaded");
    };

    public void verifyTotalInvoicesAwaitingPayment (){

    };
    public void verifyTotalConvertedLeads(){};

    public void verifyTotalProjectsInprogress (int projectsInprogress, int projectTotal){
        wait.until(ExpectedConditions.visibilityOf(totalProjectsInprogress));
        String text = totalProjectsInprogress.getText();
        System.out.println("Total Projects In Progress: "+text);
        Assert.assertEquals(projectsInprogress +" / "+projectTotal, text, "Total Projects In Progress is not match");

    };
    public void verifyTotalTasksNotFinished(){};

    public List<String> getListMenu() {
        wait.until(ExpectedConditions.visibilityOfAllElements(menuList));
        List<String> listMenuText = new ArrayList<>();

        for (WebElement menu : menuList) {
            listMenuText.add(menu.getText().trim());
        }

        return listMenuText;
    }

}
