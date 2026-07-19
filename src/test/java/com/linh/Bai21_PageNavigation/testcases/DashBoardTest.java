package com.linh.Bai21_PageNavigation.testcases;

import com.linh.Bai21_PageNavigation.pages.DashBoardPage;
import com.linh.Bai21_PageNavigation.pages.LoginPage;
import com.linh.Bai21_PageNavigation.pages.ProjectsPage;
import com.linh.common.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DashBoardTest extends BaseTest {
    private LoginPage loginPage;
    private ProjectsPage projectsPage;
    private DashBoardPage dashboardPage;
    @BeforeMethod
    public void setUp(){
        loginPage = new LoginPage(driver);
    }
    @Test
    public void testQuickStaticTotal(){
        dashboardPage = loginPage.loginCRM();
        dashboardPage.verifyNavigateToDashBoardPage();
        projectsPage = dashboardPage.clickProjectsMenu();
        projectsPage.verifyNaviageToProjectPage();
        int  notStartedTotal = projectsPage.getTotalNotStarted();
        int inProgressTotal = projectsPage.getTotalInProgress();
        int onHoldTotal = projectsPage.getTotalOnHold();
        int cancelledTotal = projectsPage.getTotalCancelled();
        int finishedTotal = projectsPage.getTotalFinished();
        int projectTotal = notStartedTotal + inProgressTotal +onHoldTotal+cancelledTotal+finishedTotal;
        System.out.println("Total project: "+projectTotal);
        dashboardPage = projectsPage.clickDashboardMenu();
        dashboardPage.verifyNavigateToDashBoardPage();
        dashboardPage.verifyTotalProjectsInprogress(inProgressTotal, projectTotal);
    }
}
