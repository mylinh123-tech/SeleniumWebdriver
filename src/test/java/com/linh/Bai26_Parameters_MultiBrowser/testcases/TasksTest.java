package com.linh.Bai26_Parameters_MultiBrowser.testcases;

import com.linh.Bai24_25_VietHamChung_WebUI.pages.DashBoardPage;
import com.linh.Bai24_25_VietHamChung_WebUI.pages.LoginPage;
import com.linh.Bai24_25_VietHamChung_WebUI.pages.TasksPage;
import com.linh.common.BaseTest;
import com.linh.constants.ConfigData;
import com.linh.utils.JsonUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TasksTest extends BaseTest {

    private LoginPage loginPage;
    private DashBoardPage dashboardPage;
    private TasksPage tasksPage;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testAddNewTaskWithProject() {
        // Lấy Project Name đã lưu ở file JSON trung gian từ testcase Add New Project.
        String projectName = JsonUtils.getValueFromJsonFile(ConfigData.PROJECT_DATA_FILE, ConfigData.KEY_PROJECT_NAME);

        String timestamp = String.valueOf(System.currentTimeMillis());
        String taskName = "AUTO_POM_ADD_TASK_" + timestamp;
        String relatedTo = "Project";
        String priority = "High";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String startDate = LocalDate.now().format(dateFormat);
        String dueDate = LocalDate.now().plusDays(7).format(dateFormat);

        // Arrange: Login vào CRM và mở trang Tasks.
        dashboardPage = loginPage.loginCRM();
        dashboardPage.verifyNavigateToDashBoardPage();
        tasksPage = new TasksPage(driver);
        tasksPage.openTasksPage()
                .verifyNavigateToTasksPage()
                .clickNewTaskButton();

        Assert.assertEquals(tasksPage.getAddNewTaskModalTitle(), "Add new task", "Tiêu đề modal tạo Task không đúng.");

        // Act: Nhập thông tin task mới và gắn task với project lấy từ file JSON.
        tasksPage.setTaskName(taskName)
                .selectPriority(priority)
                .setStartDate(startDate)
                .setDueDate(dueDate)
                .selectRelatedTo(relatedTo)
                .selectRelatedProject(projectName);

        Assert.assertTrue(tasksPage.getSelectedRelatedProject().contains(projectName),
                "Project lấy từ file JSON chưa được chọn đúng trong form tạo Task.");

        tasksPage.clickSaveButton();

        // Assert: Kiểm tra sau khi lưu, modal chi tiết và bảng Tasks hiển thị đúng dữ liệu vừa nhập.
        Assert.assertTrue(tasksPage.getTaskNameOnDetailModal().contains(taskName),
                "Modal chi tiết mở ra sau khi lưu phải hiển thị đúng tên task vừa tạo.");

        tasksPage.closeTaskDetailModal();

        Assert.assertTrue(tasksPage.isTasksTableDisplayed(), "Bảng danh sách Tasks phải hiển thị sau khi lưu.");
        Assert.assertTrue(tasksPage.isTaskDisplayed(taskName), "Task vừa tạo phải hiển thị trong danh sách Tasks.");
        Assert.assertTrue(tasksPage.getRelatedProjectOfTask(taskName).contains(projectName),
                "Task vừa tạo phải được gắn với project: " + projectName);
    }
}
