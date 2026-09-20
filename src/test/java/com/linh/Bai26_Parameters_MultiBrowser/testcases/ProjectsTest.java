package com.linh.Bai26_Parameters_MultiBrowser.testcases;

import com.linh.Bai24_25_VietHamChung_WebUI.pages.CustomersPage;
import com.linh.Bai24_25_VietHamChung_WebUI.pages.DashBoardPage;
import com.linh.Bai24_25_VietHamChung_WebUI.pages.LoginPage;
import com.linh.Bai24_25_VietHamChung_WebUI.pages.ProjectsPage;
import com.linh.common.BaseTest;
import com.linh.constants.ConfigData;
import com.linh.utils.JsonUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProjectsTest extends BaseTest {

    private LoginPage loginPage;
    private CustomersPage customersPage;
    private DashBoardPage dashboardPage;
    private ProjectsPage projectsPage;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);
    }

    @Test(priority = 1)
    public void testAddNewProjectWithCustomerFromJson() {
        String customerName = JsonUtils.getValueFromJsonFile(ConfigData.CUSTOMER_DATA_FILE, ConfigData.KEY_CUSTOMER_NAME);
        String timestamp = String.valueOf(System.currentTimeMillis());
        String projectName = "AUTO_POM_ADD_PROJECT_" + timestamp;
        String billingType = "Fixed Rate";
        String status = "Not Started";
        String startDate = "18-08-2026";
        String deadline = "31-08-2026";
        String description = "Automation project for customer: " + customerName;

        //Arrange: Login, đọc Customer từ JSON, rồi lấy Customer ID để form Add Project submit đúng dữ liệu.
        dashboardPage = loginPage.loginCRM();
        dashboardPage.verifyNavigateToDashBoardPage();
        customersPage = dashboardPage.clickCustomersMenu();
        customersPage.verifyNavigateToCustomersPage();
        String customerId = customersPage.getCustomerIdByCompanyName(customerName);

        projectsPage = customersPage.clickProjectsMenu();

        //Act: Tạo Project mới với Customer đã lấy từ JSON.
        projectsPage.verifyNavigateToProjectPage();
        projectsPage.createProject(projectName, customerId, customerName, billingType, status, startDate, deadline, description);
        String projectId = projectsPage.getCurrentProjectId();

        //Lưu Project vừa tạo ra JSON để testcase Add New Task dùng lại.
        JsonUtils.setDataToJsonFile(ConfigData.PROJECT_DATA_FILE, ConfigData.KEY_PROJECT_NAME, projectName);
        JsonUtils.setDataToJsonFile(ConfigData.PROJECT_DATA_FILE, ConfigData.KEY_PROJECT_ID, projectId);

        //Assert: Mở lại form edit để so sánh dữ liệu input ban đầu và dữ liệu đã lưu.
        projectsPage.openEditProjectPageFromCurrentProject();
        Assert.assertEquals(projectsPage.getProjectNameValue(), projectName, "Project Name lưu không đúng.");
        Assert.assertTrue(projectsPage.getSelectedCustomerValue().contains(customerName),
                "Customer lưu trong Project không đúng.");
        Assert.assertEquals(projectsPage.getSelectedBillingTypeValue(), billingType, "Billing Type lưu không đúng.");
        Assert.assertEquals(projectsPage.getSelectedStatusValue(), status, "Status lưu không đúng.");
        Assert.assertEquals(projectsPage.getStartDateValue(), startDate, "Start Date lưu không đúng.");
        Assert.assertEquals(projectsPage.getDeadlineValue(), deadline, "Deadline lưu không đúng.");
        Assert.assertEquals(projectsPage.getDescriptionValue(), description, "Description lưu không đúng.");
    }

    @Test(priority = 2)
    public void testDeleteProject() {
        String customerName = JsonUtils.getValueFromJsonFile(ConfigData.CUSTOMER_DATA_FILE, ConfigData.KEY_CUSTOMER_NAME);
        String timestamp = String.valueOf(System.currentTimeMillis());
        String projectName = "AUTO_POM_DELETE_PROJECT_" + timestamp;
        String description = "Temporary project for delete test: " + projectName;

        //Arrange: tạo riêng một Project để xóa, tránh xóa nhầm Project đang dùng cho Add Task.
        dashboardPage = loginPage.loginCRM();
        dashboardPage.verifyNavigateToDashBoardPage();
        customersPage = dashboardPage.clickCustomersMenu();
        customersPage.verifyNavigateToCustomersPage();
        String customerId = customersPage.getCustomerIdByCompanyName(customerName);
        projectsPage = customersPage.clickProjectsMenu();
        projectsPage.verifyNavigateToProjectPage();
        projectsPage.createProject(projectName, customerId, customerName,
                "Fixed Rate", "Not Started", "18-08-2026", "31-08-2026", description);

        //Act: quay lại danh sách Projects, search Project vừa tạo rồi xóa.
        projectsPage = projectsPage.clickProjectsMenu();
        projectsPage.verifyNavigateToProjectPage();
        Assert.assertTrue(projectsPage.isProjectDisplayed(projectName), "Project vừa tạo phải hiển thị trước khi xóa.");
        projectsPage.deleteProjectByName(projectName);

        //Assert: search lại và kiểm tra Project không còn trong table.
        Assert.assertTrue(projectsPage.isProjectNotDisplayed(projectName), "Project vừa xóa không được còn hiển thị.");
    }

}
