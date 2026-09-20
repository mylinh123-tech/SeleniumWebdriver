package com.linh.Bai22_23_ThucHanhPOM.pages;

import com.linh.constants.ConfigData;
import com.linh.keywords.ActionKeyword;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CustomersPage extends BasePage {
    private WebDriver driver;
    private WebDriverWait wait;


    private String customersPageUrl = "/admin/clients";
    private String addNewCustomerPageUrl = "/admin/clients/client";

    private By headerCustomersSummary = By.xpath("//h4[normalize-space()='Customers Summary']");
    private By buttonNewCustomer = By.xpath("//a[contains(@href,'/admin/clients/client') and contains(normalize-space(),'New Customer')]");
    private By tableCustomers = By.xpath("//table[@id='clients' and contains(@class,'dataTable')]");
    private By tableCustomersBody = By.cssSelector("#clients tbody");
    private By inputSearchCustomer = By.cssSelector("#clients_filter input[type='search']");

    private By tabCustomerDetails = By.cssSelector("a[href='#contact_info']");
    private By tabBillingAndShipping = By.cssSelector("a[href='#billing_and_shipping']");

    private By checkboxShowPrimaryContact = By.id("show_primary_contact");
    private By labelShowPrimaryContact = By.cssSelector("label[for='show_primary_contact']");
    private By inputCompany = By.id("company");
    private By inputVatNumber = By.id("vat");
    private By inputPhone = By.id("phonenumber");
    private By inputWebsite = By.id("website");
    private By selectGroups = By.id("groups_in[]");
    private By buttonGroupsDropdown = By.cssSelector("button[data-id='groups_in[]']");
    private By selectDefaultCurrency = By.id("default_currency");
    private By buttonDefaultCurrencyDropdown = By.cssSelector("button[data-id='default_currency']");
    private By selectDefaultLanguage = By.id("default_language");
    private By buttonDefaultLanguageDropdown = By.cssSelector("button[data-id='default_language']");
    private By textareaAddress = By.id("address");
    private By inputCity = By.id("city");
    private By inputState = By.id("state");
    private By inputZipCode = By.id("zip");
    private By selectCountry = By.id("country");
    private By buttonCountryDropdown = By.cssSelector("button[data-id='country']");

    private By textareaBillingStreet = By.id("billing_street");
    private By inputBillingCity = By.id("billing_city");
    private By inputBillingState = By.id("billing_state");
    private By inputBillingZipCode = By.id("billing_zip");
    private By selectBillingCountry = By.id("billing_country");
    private By buttonBillingCountryDropdown = By.cssSelector("button[data-id='billing_country']");
    private By linkBillingSameAsCustomerInfo = By.cssSelector("a.billing-same-as-customer");

    private By textareaShippingStreet = By.id("shipping_street");
    private By inputShippingCity = By.id("shipping_city");
    private By inputShippingState = By.id("shipping_state");
    private By inputShippingZipCode = By.id("shipping_zip");
    private By selectShippingCountry = By.id("shipping_country");
    private By buttonShippingCountryDropdown = By.cssSelector("button[data-id='shipping_country']");
    private By linkCopyBillingAddress = By.cssSelector("a.customer-copy-billing-address");

    private By buttonSaveAndCreateContact = By.cssSelector("button.save-and-add-contact.customer-form-submiter");
    private By buttonSave = By.cssSelector("button.only-save.customer-form-submiter");

    public CustomersPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //Khai báo trả về theo kiểu Fluent Page
    //Trả về chính class này, để thuận tiện quá trình gọi sử dụng tại class test
    public CustomersPage openCustomersPage() {
        driver.get(ConfigData.Base_URL + customersPageUrl);
        ActionKeyword.waitForPageLoaded(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(headerCustomersSummary));

        return this;
    }

    public CustomersPage verifyNavigateToCustomersPage() {
        ActionKeyword.waitForPageLoaded(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(headerCustomersSummary));
        wait.until(ExpectedConditions.urlContains(customersPageUrl));

        return this;
    }

    public CustomersPage clickNewCustomerButton() {
        wait.until(ExpectedConditions.elementToBeClickable(buttonNewCustomer));
        driver.findElement(buttonNewCustomer).click();
        ActionKeyword.waitForPageLoaded(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputCompany));

        return this;
    }

    public CustomersPage verifyNavigateToAddNewCustomerPage() {
        ActionKeyword.waitForPageLoaded(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputCompany));
        wait.until(ExpectedConditions.urlContains(addNewCustomerPageUrl));

        return this;
    }

    public CustomersPage searchCustomer(String keyword) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputSearchCustomer));
        driver.findElement(inputSearchCustomer).clear();
        driver.findElement(inputSearchCustomer).sendKeys(keyword);
        if (!keyword.isEmpty()) {
            wait.until(driver -> {
                String tableText = this.driver.findElement(tableCustomersBody).getText();
                return tableText.contains(keyword) || tableText.contains("No matching records found");
            });
        }

        return this;
    }

    public CustomersPage clickCustomerDetailsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(tabCustomerDetails));
        driver.findElement(tabCustomerDetails).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputCompany));

        return this;
    }

    public CustomersPage clickBillingAndShippingTab() {
        wait.until(ExpectedConditions.elementToBeClickable(tabBillingAndShipping));
        driver.findElement(tabBillingAndShipping).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(textareaBillingStreet));

        return this;
    }

    public CustomersPage setShowPrimaryContactOnDocuments(boolean isChecked) {
        clickCustomerDetailsTab();
        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(checkboxShowPrimaryContact));
        if (checkbox.isSelected() != isChecked) {
            wait.until(ExpectedConditions.elementToBeClickable(labelShowPrimaryContact));
            driver.findElement(labelShowPrimaryContact).click();
        }

        return this;
    }

    public CustomersPage fillCustomerDetails(String company, String vatNumber, String phone, String website) {
        clickCustomerDetailsTab();
        setText(inputCompany, company);
        setText(inputVatNumber, vatNumber);
        setText(inputPhone, phone);
        setText(inputWebsite, website);

        return this;
    }

    public CustomersPage selectGroups(String groupName) {
        selectPickerByText(selectGroups, "groups_in[]", groupName);

        return this;
    }

    public CustomersPage selectDefaultCurrency(String currencyName) {
        selectPickerByText(selectDefaultCurrency, "default_currency", currencyName);

        return this;
    }

    public CustomersPage selectDefaultLanguage(String languageName) {
        selectPickerByText(selectDefaultLanguage, "default_language", languageName);

        return this;
    }

    public CustomersPage fillAddress(String address, String city, String state, String zipCode, String countryName) {
        clickCustomerDetailsTab();
        setText(textareaAddress, address);
        setText(inputCity, city);
        setText(inputState, state);
        setText(inputZipCode, zipCode);
        selectPickerByText(selectCountry, "country", countryName);

        return this;
    }

    public CustomersPage fillBillingAddress(String street, String city, String state, String zipCode, String countryName) {
        clickBillingAndShippingTab();
        setText(textareaBillingStreet, street);
        setText(inputBillingCity, city);
        setText(inputBillingState, state);
        setText(inputBillingZipCode, zipCode);
        selectPickerByText(selectBillingCountry, "billing_country", countryName);

        return this;
    }

    public CustomersPage fillShippingAddress(String street, String city, String state, String zipCode, String countryName) {
        clickBillingAndShippingTab();
        setText(textareaShippingStreet, street);
        setText(inputShippingCity, city);
        setText(inputShippingState, state);
        setText(inputShippingZipCode, zipCode);
        selectPickerByText(selectShippingCountry, "shipping_country", countryName);

        return this;
    }

    public CustomersPage clickBillingSameAsCustomerInfo() {
        clickBillingAndShippingTab();
        wait.until(ExpectedConditions.elementToBeClickable(linkBillingSameAsCustomerInfo));
        driver.findElement(linkBillingSameAsCustomerInfo).click();

        return this;
    }

    public CustomersPage clickCopyBillingAddress() {
        clickBillingAndShippingTab();
        wait.until(ExpectedConditions.elementToBeClickable(linkCopyBillingAddress));
        driver.findElement(linkCopyBillingAddress).click();

        return this;
    }

    public CustomersPage clickSaveButton() {
        ActionKeyword.clickElement(driver, buttonSave, 10);

        return this;
    }

    public CustomersPage clickSaveAndCreateContactButton() {
        ActionKeyword.clickElement(driver, buttonSaveAndCreateContact, 10);

        return this;
    }

    public CustomersPage waitForCustomerProfilePage() {
        ActionKeyword.waitForPageLoaded(driver);
        wait.until(ExpectedConditions.urlMatches(".*/admin/clients/client/\\d+$"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputCompany));

        return this;
    }

    public boolean isCustomersTableDisplayed() {
        ActionKeyword.waitForPageLoaded(driver);
        return ActionKeyword.isElementPresent(driver, tableCustomers, 10);
    }

    public boolean isCustomerDisplayed(String companyName) {
        searchCustomer(companyName);
        return wait.until(driver -> this.driver.findElement(tableCustomersBody).getText().contains(companyName));
    }

    public boolean isCustomerNotDisplayed(String companyName) {
        searchCustomer(companyName);
        return wait.until(driver -> !this.driver.findElement(tableCustomersBody).getText().contains(companyName));
    }

    public String getCustomerIdByCompanyName(String companyName) {
        searchCustomer(companyName);
        By companyNameLink = getCompanyNameLink(companyName);
        String customerProfileUrl = wait.until(driver -> this.driver.findElement(companyNameLink).getAttribute("href"));
        return customerProfileUrl.substring(customerProfileUrl.lastIndexOf("/") + 1);
    }

    public CustomersPage deleteCustomerByCompanyName(String companyName) {
        searchCustomer(companyName);

        By companyNameLink = getCompanyNameLink(companyName);
        WebElement companyNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(companyNameLink));
        new Actions(driver).moveToElement(companyNameElement).perform();

        By deleteCustomerLink = getDeleteCustomerLink(companyName);
        wait.until(ExpectedConditions.elementToBeClickable(deleteCustomerLink));
        driver.findElement(deleteCustomerLink).click();

        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        ActionKeyword.waitForPageLoaded(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(headerCustomersSummary));
        searchCustomer(companyName);
        wait.until(driver -> !this.driver.findElement(tableCustomersBody).getText().contains(companyName));

        return this;
    }

    public CustomersPage deleteCustomerByHoverAndConfirmAlert(String companyName) {
        searchCustomer(companyName);

        By companyNameLink = getCompanyNameLink(companyName);
        WebElement companyNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(companyNameLink));
        new Actions(driver).moveToElement(companyNameElement).perform();

        By deleteCustomerLink = getDeleteCustomerLink(companyName);
        wait.until(ExpectedConditions.elementToBeClickable(deleteCustomerLink));
        driver.findElement(deleteCustomerLink).click();

        wait.until(ExpectedConditions.alertIsPresent());
        Alert confirmAlert = driver.switchTo().alert();
        confirmAlert.accept();

        wait.until(driver -> !this.driver.findElement(tableCustomersBody).getText().contains(companyName));

        return this;
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCompanyValue() {
        return getInputValue(inputCompany);
    }

    public String getVatNumberValue() {
        return getInputValue(inputVatNumber);
    }

    public String getPhoneValue() {
        return getInputValue(inputPhone);
    }

    public String getWebsiteValue() {
        return getInputValue(inputWebsite);
    }

    public String getAddressValue() {
        return getInputValue(textareaAddress);
    }

    public String getCityValue() {
        return getInputValue(inputCity);
    }

    public String getStateValue() {
        return getInputValue(inputState);
    }

    public String getZipCodeValue() {
        return getInputValue(inputZipCode);
    }

    public String getSelectedGroupsValue() {
        return getSelectPickerTitle(buttonGroupsDropdown);
    }

    public String getSelectedDefaultCurrencyValue() {
        return getSelectPickerTitle(buttonDefaultCurrencyDropdown);
    }

    public String getSelectedDefaultLanguageValue() {
        return getSelectPickerTitle(buttonDefaultLanguageDropdown);
    }

    public String getSelectedCountryValue() {
        return getSelectPickerTitle(buttonCountryDropdown);
    }

    public String getSelectedBillingCountryValue() {
        return getSelectPickerTitle(buttonBillingCountryDropdown);
    }

    public String getSelectedShippingCountryValue() {
        return getSelectPickerTitle(buttonShippingCountryDropdown);
    }

    private void setText(By locator, String value) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
    }

    private String getSelectPickerTitle(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator).getAttribute("title");
    }

    private String getInputValue(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator).getAttribute("value");
    }

    private By getDeleteCustomerLink(String companyName) {
        return By.xpath("//table[@id='clients']//tbody/tr[contains(., " + xpathLiteral(companyName) + ")]//a[contains(@href,'/admin/clients/delete/') and contains(@class,'_delete')]");
    }

    private By getCompanyNameLink(String companyName) {
        return By.xpath("//table[@id='clients']//tbody/tr[contains(., " + xpathLiteral(companyName) + ")]//td[contains(@class,'sorting_1')]/a[normalize-space()=" + xpathLiteral(companyName) + "]");
    }

    private void selectPickerByText(By selectLocator, String selectId, String visibleText) {
        wait.until(ExpectedConditions.presenceOfElementLocated(selectLocator));
        String js =
                "var sel=document.getElementById(arguments[0]);" +
                        "if(!sel){return 'NO_SELECT';}" +
                        "var found=false;" +
                        "if(sel.multiple){" +
                        "  for(var i=0;i<sel.options.length;i++){" +
                        "    if(sel.options[i].text.trim()===arguments[1]){sel.options[i].selected=true;found=true;break;}" +
                        "  }" +
                        "}else{" +
                        "  for(var j=0;j<sel.options.length;j++){" +
                        "    if(sel.options[j].text.trim()===arguments[1]){sel.value=sel.options[j].value;found=true;break;}" +
                        "  }" +
                        "}" +
                        "if(window.jQuery){jQuery(sel).selectpicker('refresh');jQuery(sel).trigger('change');}" +
                        "else{sel.dispatchEvent(new Event('change'));}" +
                        "return found?'OK':'NO_OPTION';";
        Object result = ((JavascriptExecutor) driver).executeScript(js, selectId, visibleText);
        if (!"OK".equals(result)) {
            throw new RuntimeException("Cannot select value '" + visibleText + "' in selectpicker #" + selectId + ". Result: " + result);
        }
    }
}
