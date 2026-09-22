package com.linh.Bai20_PageFactory.testcases;

import com.linh.Bai20_PageFactory.pages.DashBoardPage;
import com.linh.Bai20_PageFactory.pages.LoginPage;
import com.linh.common.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class DashboardTest extends BaseTest {

    private LoginPage loginPage;
    private DashBoardPage dashBoardPage;

    @Test
    public void testChecklistMenuDashboard() {
        loginPage = new LoginPage(driver);
        loginPage.loginCRM();

        dashBoardPage = new DashBoardPage(driver);
        List<String> actualMenuList = dashBoardPage.getListMenu();
        List<String> expectedMenuList = Arrays.asList(
                "Dashboard",
                "Customers",
                "Projects",
                "Tasks",
                "Contracts",
                "Sales",
                "Subscriptions",
                "Expenses",
                "Support",
                "Leads",
                "Estimate Request",
                "Knowledge Base",
                "Utilities",
                "Reports"
        );

        Assert.assertEquals(actualMenuList, expectedMenuList, "Menu list on Dashboard page does not match.");
    }
}
