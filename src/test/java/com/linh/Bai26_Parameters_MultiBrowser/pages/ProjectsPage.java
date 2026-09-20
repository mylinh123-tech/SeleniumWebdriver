package com.linh.Bai26_Parameters_MultiBrowser.pages;

import com.linh.constants.ConfigData;
import com.linh.keywords.WebUI;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class ProjectsPage extends BasePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private String projectsPageUrl= "/admin/projects";
    private String addNewProjectPageUrl = "/admin/projects/project";
    private String projectProfilePageUrl = "/admin/projects/view/";
    private By headerProjectPage = By.xpath("//span[normalize-space()='Projects Summary']");
    private By buttonNewProject = By.xpath("//a[contains(@href,'/admin/projects/project') and contains(normalize-space(),'New Project')]");
    private By tableProjectsBody = By.cssSelector("#projects tbody");
    private By inputSearchProject = By.cssSelector("#projects_filter input[type='search']");
    private By inputProjectName = By.id("name");
    private By selectCustomer = By.id("clientid");
    private By buttonCustomerDropdown = By.cssSelector("button[data-id='clientid']");
    private By inputSelectPickerSearch = By.cssSelector("div.bootstrap-select.open input[type='search']");
    private By selectBillingType = By.id("billing_type");
    private By buttonBillingTypeDropdown = By.cssSelector("button[data-id='billing_type']");
    private By selectStatus = By.id("status");
    private By buttonStatusDropdown = By.cssSelector("button[data-id='status']");
    private By inputStartDate = By.id("start_date");
    private By inputDeadline = By.id("deadline");
    private By textareaDescription = By.cssSelector("body");
    private By iframeDescription = By.cssSelector("iframe[id^='description_ifr']");
    private By buttonSave = By.cssSelector("button[type='submit']");
    private By totalNotStarted = By.xpath("//span[contains(@class,'project-status') and normalize-space()= 'Not Started']/preceding-sibling::span");
    private By totalInProgress = By.xpath("//span[contains(@class,'project-status') and normalize-space()= 'In Progress']/preceding-sibling::span");
    private By totalOnHold = By.xpath("//span[contains(@class,'project-status') and normalize-space()= 'On Hold']/preceding-sibling::span");
    private By totalCancelled= By.xpath("//span[contains(@class,'project-status') and normalize-space()= 'Cancelled']/preceding-sibling::span");
    private By totalFinished= By.xpath("//span[contains(@class,'project-status') and normalize-space()= 'Finished']/preceding-sibling::span");

    public ProjectsPage ( WebDriver driver) {
        super(driver);
        this.driver = driver;
        new WebUI(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public void verifyNavigateToProjectPage(){
        WebUI.waitForPageLoaded();
        Assert.assertEquals(WebUI.getElementText(headerProjectPage), "Projects Summary","Projects Summary is not match");
        Assert.assertEquals(WebUI.getCurrentURL(), ConfigData.Base_URL+projectsPageUrl, "Project page url was not loaded");

    }

    public ProjectsPage clickNewProjectButton() {
        WebUI.clickElement(buttonNewProject);
        WebUI.waitForPageLoaded();
        WebUI.waitForElementVisible(inputProjectName);

        return this;
    }

    public ProjectsPage verifyNavigateToAddNewProjectPage() {
        WebUI.waitForPageLoaded();
        WebUI.waitForElementVisible(inputProjectName);
        WebUI.waitForCurrentURLContains(addNewProjectPageUrl);

        return this;
    }

    public ProjectsPage fillProjectName(String projectName) {
        WebUI.setText(inputProjectName, projectName);

        return this;
    }

    public ProjectsPage createProject(String projectName, String customerId, String customerName, String billingType,
                                      String status, String startDate, String deadline, String description) {
        clickNewProjectButton()
                .verifyNavigateToAddNewProjectPage()
                .fillProjectName(projectName)
                .selectCustomer(customerId, customerName)
                .selectBillingType(billingType)
                .selectStatus(status)
                .fillProjectDates(startDate, deadline)
                .fillDescription(description)
                .clickSaveButton()
                .verifyProjectProfilePageOpened(projectName);

        return this;
    }

    public ProjectsPage selectCustomer(String customerName) {
        selectPickerBySearchText(selectCustomer, buttonCustomerDropdown, customerName);

        return this;
    }

    public ProjectsPage selectCustomer(String customerId, String customerName) {
        WebUI.waitForElementPresent(selectCustomer);
        String js =
                "var sel=document.getElementById('clientid');" +
                        "var opt=new Option(arguments[1], arguments[0], true, true);" +
                        "sel.add(opt);" +
                        "sel.value=arguments[0];" +
                        "if(window.jQuery){jQuery(sel).trigger('change');jQuery(sel).selectpicker('refresh');}" +
                        "else{sel.dispatchEvent(new Event('change'));}" +
                        "return sel.value;";
        Object selectedValue = ((JavascriptExecutor) driver).executeScript(js, customerId, customerName);
        if (!customerId.equals(String.valueOf(selectedValue))) {
            throw new RuntimeException("Cannot select customer '" + customerName + "' with id '" + customerId + "'.");
        }
        WebUI.waitForAttributeContains(buttonCustomerDropdown, "title", customerName);

        return this;
    }

    public ProjectsPage selectBillingType(String billingType) {
        selectPickerByText(selectBillingType, "billing_type", billingType);

        return this;
    }

    public ProjectsPage selectStatus(String status) {
        selectPickerByText(selectStatus, "status", status);

        return this;
    }

    public ProjectsPage fillProjectDates(String startDate, String deadline) {
        WebUI.setText(inputStartDate, startDate);
        WebUI.setText(inputDeadline, deadline);

        return this;
    }

    public ProjectsPage fillDescription(String description) {
        WebUI.waitForElementPresent(iframeDescription);
        String js =
                "if(window.tinymce && tinymce.get('description')){" +
                        "tinymce.get('description').setContent(arguments[0]);" +
                        "tinymce.get('description').save();" +
                        "tinymce.get('description').fire('change');" +
                        "return tinymce.get('description').getContent({format:'text'});" +
                        "}" +
                        "var iframe=document.querySelector(\"iframe[id^='description_ifr']\");" +
                        "if(!iframe){return 'NO_IFRAME';}" +
                        "var body=iframe.contentDocument.body;" +
                        "body.innerText=arguments[0];" +
                        "body.dispatchEvent(new Event('input',{bubbles:true}));" +
                        "return body.innerText;";
        Object actualDescription = ((JavascriptExecutor) driver).executeScript(js, description);
        if (!description.equals(String.valueOf(actualDescription).trim())) {
            throw new RuntimeException("Cannot set Project Description. Actual: " + actualDescription);
        }

        return this;
    }

    public ProjectsPage clickSaveButton() {
        WebUI.clickElement(buttonSave, 10);

        return this;
    }

    public ProjectsPage waitForProjectProfilePage() {
        WebUI.waitForPageLoaded();
        wait.until(ExpectedConditions.urlMatches(".*/admin/projects/view/\\d+.*"));

        return this;
    }

    public ProjectsPage verifyProjectProfilePageOpened(String projectName) {
        waitForProjectProfilePage();
        wait.until(ExpectedConditions.titleContains(projectName));

        return this;
    }

    public ProjectsPage openEditProjectPageFromCurrentProject() {
        String projectId = getCurrentProjectId();
        WebUI.openURL(ConfigData.Base_URL + addNewProjectPageUrl + "/" + projectId);
        WebUI.waitForPageLoaded();
        WebUI.waitForElementVisible(inputProjectName);

        return this;
    }

    public ProjectsPage openProjectProfilePage(String projectId) {
        WebUI.openURL(ConfigData.Base_URL + projectProfilePageUrl + projectId);
        WebUI.waitForPageLoaded();
        WebUI.waitForCurrentURLContains(projectProfilePageUrl + projectId);

        return this;
    }

    public ProjectsPage searchProject(String projectName) {
        WebUI.waitForElementVisible(inputSearchProject);
        WebUI.setText(inputSearchProject, projectName);
        wait.until(driver -> this.driver.findElement(tableProjectsBody).getText().contains(projectName)
                || this.driver.findElement(tableProjectsBody).getText().contains("No matching records found"));

        return this;
    }

    public boolean isProjectDisplayed(String projectName) {
        searchProject(projectName);
        return wait.until(driver -> this.driver.findElement(tableProjectsBody).getText().contains(projectName));
    }

    public boolean isProjectNotDisplayed(String projectName) {
        searchProject(projectName);
        return wait.until(driver -> !this.driver.findElement(tableProjectsBody).getText().contains(projectName));
    }

    public ProjectsPage deleteProjectByName(String projectName) {
        searchProject(projectName);

        By projectNameLink = getProjectNameLink(projectName);
        WebUI.hover(projectNameLink);

        By deleteProjectLink = getDeleteProjectLink(projectName);
        WebElement deleteElement = wait.until(ExpectedConditions.presenceOfElementLocated(deleteProjectLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteElement);

        WebUI.acceptAlert();
        WebUI.waitForPageLoaded();
        WebUI.waitForElementVisible(headerProjectPage);

        return this;
    }

    public String getProjectNameValue() {
        return WebUI.getInputValue(inputProjectName);
    }

    public String getSelectedCustomerValue() {
        return getSelectPickerTitle(buttonCustomerDropdown);
    }

    public String getSelectedBillingTypeValue() {
        return getSelectPickerTitle(buttonBillingTypeDropdown);
    }

    public String getSelectedStatusValue() {
        return getSelectPickerTitle(buttonStatusDropdown);
    }

    public String getStartDateValue() {
        return WebUI.getInputValue(inputStartDate);
    }

    public String getDeadlineValue() {
        return WebUI.getInputValue(inputDeadline);
    }

    public String getDescriptionValue() {
        WebUI.switchToFrame(iframeDescription);
        try {
            return WebUI.getElementText(textareaDescription);
        } finally {
            WebUI.switchToDefaultContent();
        }
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentProjectId() {
        return getProjectIdFromCurrentUrl();
    }

    public int getTotalNotStarted(){
        int notStartedTotal = Integer.parseInt(WebUI.getElementText(totalNotStarted));

        return notStartedTotal;
    }
    public int getTotalInProgress(){
        int inProgressTotal = Integer.parseInt(WebUI.getElementText(totalInProgress));

        return inProgressTotal;
    }
    public int getTotalOnHold(){
        int onHoldTotal = Integer.parseInt(WebUI.getElementText(totalOnHold));

        return onHoldTotal;
    }
    public int getTotalCancelled(){
        int cancelledTotal = Integer.parseInt(WebUI.getElementText(totalCancelled));

        return cancelledTotal;
    }
    public int getTotalFinished(){
        int finishedTotal = Integer.parseInt(WebUI.getElementText(totalFinished));

        return finishedTotal;
    }



    private String getSelectPickerTitle(By locator) {
        return WebUI.getElementAttribute(locator, "title");
    }

    private String getProjectIdFromCurrentUrl() {
        String currentUrl = WebUI.getCurrentURL();
        String[] parts = currentUrl.split("/");
        for (int i = 0; i < parts.length; i++) {
            if ("view".equals(parts[i]) && i + 1 < parts.length) {
                return parts[i + 1].split("\\?")[0];
            }
        }
        throw new RuntimeException("Cannot get Project ID from current URL: " + currentUrl);
    }

    private By getProjectNameLink(String projectName) {
        return By.xpath("//table[@id='projects']//tbody/tr[contains(., " + xpathLiteral(projectName) + ")]//a[normalize-space()="
                + xpathLiteral(projectName) + " or contains(normalize-space(), " + xpathLiteral(projectName) + ")]");
    }

    private By getDeleteProjectLink(String projectName) {
        return By.xpath("//table[@id='projects']//tbody/tr[contains(., " + xpathLiteral(projectName) + ")]//a[contains(@href,'/admin/projects/delete/') and contains(@class,'_delete')]");
    }

    private void selectPickerByText(By selectLocator, String selectId, String visibleText) {
        WebUI.waitForElementPresent(selectLocator);
        String js =
                "var sel=document.getElementById(arguments[0]);" +
                        "if(!sel){return 'NO_SELECT';}" +
                        "var found=false;" +
                        "for(var i=0;i<sel.options.length;i++){" +
                        "  if(sel.options[i].text.trim()===arguments[1]){sel.value=sel.options[i].value;found=true;break;}" +
                        "}" +
                        "if(window.jQuery){jQuery(sel).selectpicker('refresh');jQuery(sel).trigger('change');}" +
                        "else{sel.dispatchEvent(new Event('change'));}" +
                        "return found?'OK':'NO_OPTION';";
        Object result = ((JavascriptExecutor) driver).executeScript(js, selectId, visibleText);
        if (!"OK".equals(result)) {
            throw new RuntimeException("Cannot select value '" + visibleText + "' in selectpicker #" + selectId + ". Result: " + result);
        }
    }

    private void selectPickerBySearchText(By selectLocator, By dropdownButton, String visibleText) {
        WebUI.waitForElementPresent(selectLocator);
        WebUI.clickElement(dropdownButton);

        WebUI.waitForElementVisible(inputSelectPickerSearch);
        String searchKeyword = visibleText.length() > 20 ? visibleText.substring(0, 20) : visibleText;
        WebUI.setText(inputSelectPickerSearch, searchKeyword);

        By option = By.xpath("//div[contains(@class,'bootstrap-select') and contains(@class,'open')]//span[contains(normalize-space(), "
                + xpathLiteral(visibleText) + ")]/ancestor::a[1]");
        try {
            wait.until(ExpectedConditions.elementToBeClickable(option));
        } catch (TimeoutException exception) {
            String dropdownText = driver.findElement(By.cssSelector("div.bootstrap-select.open")).getText();
            String selectHtml = driver.findElement(selectLocator).getAttribute("outerHTML");
            throw new RuntimeException("Cannot find customer option '" + visibleText + "'. Dropdown text: "
                    + dropdownText + ". Select HTML: " + selectHtml, exception);
        }
        WebUI.clickElement(option);

        WebUI.waitForAttributeContains(dropdownButton, "title", visibleText);
    }


}
