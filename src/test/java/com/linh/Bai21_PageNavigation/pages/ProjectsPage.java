package com.linh.Bai21_PageNavigation.pages;

import com.linh.constants.ConfigData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class ProjectsPage extends BasePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private String projectsPageUrl= "/admin/projects";
    private By headerProjectPage = By.xpath("//span[normalize-space()='Projects Summary']");
    private By totalNotStarted = By.xpath("//span[contains(@class,'project-status') and normalize-space()= 'Not Started']/preceding-sibling::span");
    private By totalInProgress = By.xpath("//span[contains(@class,'project-status') and normalize-space()= 'In Progress']/preceding-sibling::span");
    private By totalOnHold = By.xpath("//span[contains(@class,'project-status') and normalize-space()= 'On Hold']/preceding-sibling::span");
    private By totalCancelled= By.xpath("//span[contains(@class,'project-status') and normalize-space()= 'Cancelled']/preceding-sibling::span");
    private By totalFinished= By.xpath("//span[contains(@class,'project-status') and normalize-space()= 'Finished']/preceding-sibling::span");

    public ProjectsPage ( WebDriver driver) {
        super(driver);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public void verifyNaviageToProjectPage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(headerProjectPage));
        Assert.assertEquals(driver.findElement(headerProjectPage).getText(), "Projects Summary","Projects Summary is not match");
        Assert.assertEquals(driver.getCurrentUrl(), ConfigData.Base_URL+projectsPageUrl, "Project page url was not loaded");

    }
    public int getTotalNotStarted(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(totalNotStarted));
        int notStartedTotal = Integer.parseInt(driver.findElement(totalNotStarted).getText());
        System.out.println("Total not started:" + notStartedTotal);
        return notStartedTotal;
    }
    public int getTotalInProgress(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(totalInProgress));
        int inProgressTotal = Integer.parseInt(driver.findElement(totalInProgress).getText());
        System.out.println("Total in progress:" +inProgressTotal );
        return inProgressTotal;
    }
    public int getTotalOnHold(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(totalOnHold));
        int onHoldTotal = Integer.parseInt(driver.findElement(totalOnHold).getText());
        System.out.println("Total On hold:" + onHoldTotal);
        return onHoldTotal;
    }
    public int getTotalCancelled(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(totalCancelled));
        int cancelledTotal = Integer.parseInt(driver.findElement(totalCancelled).getText());
        System.out.println("Total cancelled:" + cancelledTotal);
        return cancelledTotal;
    }
    public int getTotalFinished(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(totalFinished));
        int finishedTotal = Integer.parseInt(driver.findElement(totalFinished).getText());
        System.out.println("Total finished:" + finishedTotal);
        return finishedTotal;
    }



}
