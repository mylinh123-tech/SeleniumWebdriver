package com.linh.Bai22_23_ThucHanhPOM.testcases;

import com.linh.Bai22_23_ThucHanhPOM.pages.DashBoardPage;
import com.linh.Bai22_23_ThucHanhPOM.pages.LoginPage;
import com.linh.Bai22_23_ThucHanhPOM.pages.ProjectsPage;
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
    public void test_E2E_VerifyTotalProjectsInprogress(){
        dashboardPage = loginPage.loginCRM();
        dashboardPage.verifyNavigateToDashBoardPage();
        projectsPage = dashboardPage.clickProjectsMenu();
        projectsPage.verifyNavigateToProjectPage();
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
    @Test
    public void test_E2E_VerifyTotalInvoicesAwaitingPayment(){
        dashboardPage = loginPage.loginCRM();
        dashboardPage.verifyNavigateToDashBoardPage();
        dashboardPage.verifyTotalInvoicesAwaitingPayment("3 / 6");
    }
    @Test
    public void test_E2E_VerifyTotalConvertedLeads(){
        dashboardPage = loginPage.loginCRM();
        dashboardPage.verifyNavigateToDashBoardPage();
        dashboardPage.verifyTotalConvertedLeads("0 / 0");
    }
    @Test
    public void test_E2E_VerifyTotalTasksNotFinished(){
        dashboardPage = loginPage.loginCRM();
        dashboardPage.verifyNavigateToDashBoardPage();
        dashboardPage.verifyTotalTasksNotFinished("232 / 233");
    }
}
