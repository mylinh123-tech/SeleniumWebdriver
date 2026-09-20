package com.linh.Bai24_25_VietHamChung_WebUI.pages;

import com.linh.constants.ConfigData;
import com.linh.keywords.WebUI;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class TasksPage extends BasePage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Wait<WebDriver> ajaxSearchWait;

    private String tasksPageUrl = "/admin/tasks";

    private By headerTasksSummary = By.xpath("//span[normalize-space()='Tasks Summary']");
    private By buttonNewTask = By.xpath("//a[contains(@class,'btn-primary') and contains(normalize-space(),'New Task')]");
    private By tableTasks = By.xpath("//table[@id='tasks']");
    private By tableTasksBody = By.xpath("//table[@id='tasks']//tbody");
    private By inputSearchTask = By.xpath("//div[@id='tasks_filter']//input[@type='search']");

    // Form Add New Task nằm trong modal, được render động sau khi bấm nút New Task.
    private By modalTask = By.xpath("//div[@id='_task_modal' and (contains(@class,'in') or contains(@class,'show'))]");
    private By modalTaskTitle = By.xpath("//div[@id='_task_modal']//h4[contains(@class,'modal-title')]");
    private By inputTaskName = By.xpath("//div[@id='_task_modal']//input[@id='name']");
    private By inputStartDate = By.xpath("//div[@id='_task_modal']//input[@id='startdate']");
    private By inputDueDate = By.xpath("//div[@id='_task_modal']//input[@id='duedate']");
    private By buttonPriorityDropdown = By.xpath("//div[@id='_task_modal' and (contains(@class,'in') or contains(@class,'show'))]//button[@data-id='priority']");
    private By buttonRelatedToDropdown = By.xpath("//div[@id='_task_modal' and (contains(@class,'in') or contains(@class,'show'))]//button[@data-id='rel_type']");
    private By buttonRelatedItemDropdown = By.xpath("//div[@id='_task_modal' and (contains(@class,'in') or contains(@class,'show'))]//button[@data-id='rel_id']");
    private By inputRelatedItemSearch = By.xpath("//div[@id='_task_modal' and (contains(@class,'in') or contains(@class,'show'))]//select[@id='rel_id']/parent::div//div[contains(@class,'bs-searchbox')]/input");
    private By buttonSave = By.xpath("//div[@id='_task_modal']//button[@type='submit' and contains(@class,'btn-primary')]");
    private By modalBackdrop = By.xpath("//div[contains(@class,'modal-backdrop')]");

    // Modal chi tiết task tự động mở sau khi lưu task mới.
    private By modalTaskDetail = By.xpath("//div[@id='task-modal' and (contains(@class,'in') or contains(@class,'show'))]");
    private By modalTaskDetailTitle = By.xpath("//div[@id='task-modal']//h4[contains(@class,'modal-title')]");
    private By buttonCloseTaskDetail = By.xpath("//div[@id='task-modal']//button[contains(@class,'close')]");
    private By floatAlert = By.xpath("//*[contains(@class,'float-alert')]");

    public TasksPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        new WebUI(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        ajaxSearchWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
    }

    public TasksPage openTasksPage() {
        WebUI.openURL(ConfigData.Base_URL + tasksPageUrl);
        WebUI.waitForPageLoaded();
        WebUI.waitForElementVisible(headerTasksSummary);

        return this;
    }

    public TasksPage verifyNavigateToTasksPage() {
        WebUI.waitForPageLoaded();
        WebUI.waitForElementVisible(headerTasksSummary);
        WebUI.waitForCurrentURLContains(tasksPageUrl);

        return this;
    }

    public TasksPage verifyNavigateToTaskPage() {
        verifyNavigateToTasksPage();
        Assert.assertEquals(WebUI.getCurrentURL(), ConfigData.Base_URL + tasksPageUrl, "Task page url was not loaded");

        return this;
    }

    public TasksPage clickNewTaskButton() {
        WebUI.clickElement(buttonNewTask);
        // Chờ modal chạy xong hiệu ứng fade để tránh nhập liệu khi form chưa sẵn sàng.
        WebUI.waitForElementVisible(modalTask);
        WebUI.waitForElementClickable(inputTaskName);

        return this;
    }

    public String getAddNewTaskModalTitle() {
        return WebUI.getElementText(modalTaskTitle).trim();
    }

    public TasksPage setTaskName(String taskName) {
        WebUI.setText(inputTaskName, taskName);

        return this;
    }

    public TasksPage setStartDate(String startDate) {
        WebUI.setText(inputStartDate, startDate);
        closeDatePicker();

        return this;
    }

    public TasksPage setDueDate(String dueDate) {
        WebUI.setText(inputDueDate, dueDate);
        closeDatePicker();

        return this;
    }

    public TasksPage selectPriority(String priority) {
        selectPickerByText(buttonPriorityDropdown, "priority", priority);

        return this;
    }

    public TasksPage selectRelatedTo(String relatedType) {
        selectPickerByText(buttonRelatedToDropdown, "rel_type", relatedType);

        return this;
    }

    /**
     * Ô chọn Project là selectpicker ajax-search, nên cần mở dropdown rồi gõ tên project để load option.
     */
    public TasksPage selectRelatedProject(String projectName) {
        WebUI.clickElement(buttonRelatedItemDropdown);
        WebUI.waitForElementVisible(inputRelatedItemSearch);

        By optionProject = getSelectPickerOptionContains("rel_id", projectName);
        ajaxSearchWait.until(driver -> {
            List<WebElement> options = driver.findElements(optionProject);
            if (!options.isEmpty() && options.get(0).isDisplayed()) {
                return true;
            }
            typeKeywordToRelatedItemSearchBox(projectName);
            return false;
        });

        WebUI.clickElement(optionProject);
        WebUI.waitForAttributeContains(buttonRelatedItemDropdown, "title", projectName);

        return this;
    }

    public String getSelectedRelatedProject() {
        return WebUI.getElementAttribute(buttonRelatedItemDropdown, "title");
    }

    public TasksPage clickSaveButton() {
        WebUI.clickElement(buttonSave, 10);
        WebUI.waitForElementInvisible(modalTask);
        WebUI.waitForElementVisible(modalTaskDetailTitle);

        return this;
    }

    public String getTaskNameOnDetailModal() {
        return WebUI.getElementText(modalTaskDetailTitle).trim();
    }

    public TasksPage closeTaskDetailModal() {
        WebUI.waitForElementInvisible(floatAlert);
        WebUI.clickElement(buttonCloseTaskDetail);
        WebUI.waitForElementInvisible(modalTaskDetail);
        WebUI.waitForElementInvisible(modalBackdrop);
        WebUI.waitForPageLoaded();

        return this;
    }

    public TasksPage searchTask(String keyword) {
        WebUI.waitForElementVisible(inputSearchTask);
        WebUI.setText(inputSearchTask, keyword);
        if (!keyword.isEmpty()) {
            wait.until(driver -> {
                String tableText = this.driver.findElement(tableTasksBody).getText();
                return tableText.contains(keyword) || tableText.contains("No matching records found");
            });
        }

        return this;
    }

    public boolean isTasksTableDisplayed() {
        WebUI.waitForPageLoaded();
        return WebUI.isElementVisible(tableTasks);
    }

    public boolean isTaskDisplayed(String taskName) {
        searchTask(taskName);
        return wait.until(driver -> this.driver.findElement(tableTasksBody).getText().contains(taskName));
    }

    public String getRelatedProjectOfTask(String taskName) {
        searchTask(taskName);
        By relatedProjectLink = By.xpath("//table[@id='tasks']//tbody/tr[contains(., " + xpathLiteral(taskName) + ")]//a[contains(@class,'task-table-related')]");
        return WebUI.getElementText(relatedProjectLink).trim();
    }

    public TasksPage addNewTaskForProject(String taskName, String projectName, String startDate, String dueDate, String priority) {
        openTasksPage()
                .verifyNavigateToTasksPage()
                .clickNewTaskButton()
                .setTaskName(taskName)
                .selectPriority(priority)
                .setStartDate(startDate)
                .setDueDate(dueDate)
                .selectRelatedTo("Project")
                .selectRelatedProject(projectName)
                .clickSaveButton();

        return this;
    }

    public TasksPage addNewTaskForProject(String taskName, String projectId, String projectName, String startDate, String dueDate, String priority) {
        return addNewTaskForProject(taskName, projectName, startDate, dueDate, priority);
    }

    private void typeKeywordToRelatedItemSearchBox(String keyword) {
        List<WebElement> searchBoxes = driver.findElements(inputRelatedItemSearch);
        if (searchBoxes.isEmpty() || !searchBoxes.get(0).isDisplayed()) {
            WebUI.clickElement(buttonRelatedItemDropdown);
            return;
        }
        WebUI.setText(inputRelatedItemSearch, keyword);
    }

    /**
     * Date picker sau khi nhập ngày có thể còn mở và che control phía dưới.
     * TAB chuyển focus khỏi ô ngày để date picker tự đóng, nhưng không đóng modal Add Task.
     */
    private void closeDatePicker() {
        WebUI.pressKey(Keys.TAB);
    }


    private void selectPickerByText(By buttonDropdown, String selectId, String visibleText) {
        WebUI.clickElement(buttonDropdown);

        By option = getSelectPickerOption(selectId, visibleText);
        WebUI.clickElement(option);

        WebUI.waitForAttribute(buttonDropdown, "title", visibleText);
    }

    private By getSelectPickerOption(String selectId, String visibleText) {
        return By.xpath("//div[@id='_task_modal' and (contains(@class,'in') or contains(@class,'show'))]//select[@id='" + selectId + "']/parent::div//ul[contains(@class,'dropdown-menu')]//span[@class='text' and normalize-space()=" + xpathLiteral(visibleText) + "]");
    }

    private By getSelectPickerOptionContains(String selectId, String partialText) {
        return By.xpath("//div[@id='_task_modal' and (contains(@class,'in') or contains(@class,'show'))]//select[@id='" + selectId + "']/parent::div//ul[contains(@class,'dropdown-menu')]//span[@class='text' and contains(normalize-space()," + xpathLiteral(partialText) + ")]");
    }
}
