package com.linh.Bai22_23_ThucHanhPOM.pages;

import com.linh.constants.ConfigData;
import com.linh.keywords.ActionKeyword;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        ajaxSearchWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
    }

    public TasksPage openTasksPage() {
        driver.get(ConfigData.Base_URL + tasksPageUrl);
        ActionKeyword.waitForPageLoaded(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(headerTasksSummary));

        return this;
    }

    public TasksPage verifyNavigateToTasksPage() {
        ActionKeyword.waitForPageLoaded(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(headerTasksSummary));
        wait.until(ExpectedConditions.urlContains(tasksPageUrl));

        return this;
    }

    public TasksPage verifyNavigateToTaskPage() {
        verifyNavigateToTasksPage();
        Assert.assertEquals(driver.getCurrentUrl(), ConfigData.Base_URL + tasksPageUrl, "Task page url was not loaded");

        return this;
    }

    public TasksPage clickNewTaskButton() {
        wait.until(ExpectedConditions.elementToBeClickable(buttonNewTask));
        driver.findElement(buttonNewTask).click();
        // Chờ modal chạy xong hiệu ứng fade để tránh nhập liệu khi form chưa sẵn sàng.
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalTask));
        wait.until(ExpectedConditions.elementToBeClickable(inputTaskName));

        return this;
    }

    public String getAddNewTaskModalTitle() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalTaskTitle));
        return driver.findElement(modalTaskTitle).getText().trim();
    }

    public TasksPage setTaskName(String taskName) {
        setText(inputTaskName, taskName);

        return this;
    }

    public TasksPage setStartDate(String startDate) {
        setText(inputStartDate, startDate);
        closeDatePicker();

        return this;
    }

    public TasksPage setDueDate(String dueDate) {
        setText(inputDueDate, dueDate);
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
        wait.until(ExpectedConditions.elementToBeClickable(buttonRelatedItemDropdown));
        driver.findElement(buttonRelatedItemDropdown).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputRelatedItemSearch));

        By optionProject = getSelectPickerOptionContains("rel_id", projectName);
        ajaxSearchWait.until(driver -> {
            List<WebElement> options = driver.findElements(optionProject);
            if (!options.isEmpty() && options.get(0).isDisplayed()) {
                return true;
            }
            typeKeywordToRelatedItemSearchBox(projectName);
            return false;
        });

        driver.findElement(optionProject).click();
        wait.until(ExpectedConditions.attributeContains(buttonRelatedItemDropdown, "title", projectName));

        return this;
    }

    public String getSelectedRelatedProject() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(buttonRelatedItemDropdown));
        return driver.findElement(buttonRelatedItemDropdown).getAttribute("title");
    }

    public TasksPage clickSaveButton() {
        ActionKeyword.clickElement(driver, buttonSave, 10);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(modalTask));
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalTaskDetailTitle));

        return this;
    }

    public String getTaskNameOnDetailModal() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalTaskDetailTitle));
        return driver.findElement(modalTaskDetailTitle).getText().trim();
    }

    public TasksPage closeTaskDetailModal() {
        wait.until(driver -> driver.findElements(floatAlert).stream().noneMatch(WebElement::isDisplayed));
        wait.until(ExpectedConditions.elementToBeClickable(buttonCloseTaskDetail));
        driver.findElement(buttonCloseTaskDetail).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(modalTaskDetail));
        wait.until(driver -> driver.findElements(modalBackdrop).stream().noneMatch(WebElement::isDisplayed));
        ActionKeyword.waitForPageLoaded(driver);

        return this;
    }

    public TasksPage searchTask(String keyword) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputSearchTask));
        driver.findElement(inputSearchTask).clear();
        driver.findElement(inputSearchTask).sendKeys(keyword);
        if (!keyword.isEmpty()) {
            wait.until(driver -> {
                String tableText = this.driver.findElement(tableTasksBody).getText();
                return tableText.contains(keyword) || tableText.contains("No matching records found");
            });
        }

        return this;
    }

    public boolean isTasksTableDisplayed() {
        ActionKeyword.waitForPageLoaded(driver);
        return ActionKeyword.isElementPresent(driver, tableTasks, 10);
    }

    public boolean isTaskDisplayed(String taskName) {
        searchTask(taskName);
        return wait.until(driver -> this.driver.findElement(tableTasksBody).getText().contains(taskName));
    }

    public String getRelatedProjectOfTask(String taskName) {
        searchTask(taskName);
        By relatedProjectLink = By.xpath("//table[@id='tasks']//tbody/tr[contains(., " + xpathLiteral(taskName) + ")]//a[contains(@class,'task-table-related')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(relatedProjectLink));
        return driver.findElement(relatedProjectLink).getText().trim();
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
            driver.findElement(buttonRelatedItemDropdown).click();
            return;
        }
        searchBoxes.get(0).clear();
        searchBoxes.get(0).sendKeys(keyword);
    }

    /**
     * Date picker sau khi nhập ngày có thể còn mở và che control phía dưới.
     * TAB chuyển focus khỏi ô ngày để date picker tự đóng, nhưng không đóng modal Add Task.
     */
    private void closeDatePicker() {
        new Actions(driver).sendKeys(Keys.TAB).perform();
    }

    private void setText(By locator, String value) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
    }

    private void selectPickerByText(By buttonDropdown, String selectId, String visibleText) {
        wait.until(ExpectedConditions.elementToBeClickable(buttonDropdown));
        driver.findElement(buttonDropdown).click();

        By option = getSelectPickerOption(selectId, visibleText);
        wait.until(ExpectedConditions.elementToBeClickable(option));
        driver.findElement(option).click();

        wait.until(ExpectedConditions.attributeToBe(buttonDropdown, "title", visibleText));
    }

    private By getSelectPickerOption(String selectId, String visibleText) {
        return By.xpath("//div[@id='_task_modal' and (contains(@class,'in') or contains(@class,'show'))]//select[@id='" + selectId + "']/parent::div//ul[contains(@class,'dropdown-menu')]//span[@class='text' and normalize-space()=" + xpathLiteral(visibleText) + "]");
    }

    private By getSelectPickerOptionContains(String selectId, String partialText) {
        return By.xpath("//div[@id='_task_modal' and (contains(@class,'in') or contains(@class,'show'))]//select[@id='" + selectId + "']/parent::div//ul[contains(@class,'dropdown-menu')]//span[@class='text' and contains(normalize-space()," + xpathLiteral(partialText) + ")]");
    }
}
