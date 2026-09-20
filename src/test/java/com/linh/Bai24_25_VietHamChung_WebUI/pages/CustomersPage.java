package com.linh.Bai24_25_VietHamChung_WebUI.pages;

import com.linh.constants.ConfigData;
import com.linh.keywords.WebUI;
import org.openqa.selenium.*;
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
        new WebUI(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //Khai báo trả về theo kiểu Fluent Page
    //Trả về chính class này, để thuận tiện quá trình gọi sử dụng tại class test
    public CustomersPage openCustomersPage() {
        WebUI.openURL(ConfigData.Base_URL + customersPageUrl);
        WebUI.waitForPageLoaded();
        WebUI.waitForElementVisible(headerCustomersSummary);

        return this;
    }

    public CustomersPage verifyNavigateToCustomersPage() {
        WebUI.waitForPageLoaded();
        WebUI.waitForElementVisible(headerCustomersSummary);
        WebUI.waitForCurrentURLContains(customersPageUrl);

        return this;
    }

    public CustomersPage clickNewCustomerButton() {
        WebUI.clickElement(buttonNewCustomer);
        WebUI.waitForPageLoaded();
        WebUI.waitForElementVisible(inputCompany);

        return this;
    }

    public CustomersPage verifyNavigateToAddNewCustomerPage() {
        WebUI.waitForPageLoaded();
        WebUI.waitForElementVisible(inputCompany);
        WebUI.waitForCurrentURLContains(addNewCustomerPageUrl);

        return this;
    }

    public CustomersPage searchCustomer(String keyword) {
        WebUI.waitForElementVisible(inputSearchCustomer);
        WebUI.setText(inputSearchCustomer, keyword);
        if (!keyword.isEmpty()) {
            wait.until(driver -> {
                String tableText = this.driver.findElement(tableCustomersBody).getText();
                return tableText.contains(keyword) || tableText.contains("No matching records found");
            });
        }

        return this;
    }

    public CustomersPage clickCustomerDetailsTab() {
        WebUI.clickElement(tabCustomerDetails);
        WebUI.waitForElementVisible(inputCompany);

        return this;
    }

    public CustomersPage clickBillingAndShippingTab() {
        WebUI.clickElement(tabBillingAndShipping);
        WebUI.waitForElementVisible(textareaBillingStreet);

        return this;
    }

    public CustomersPage setShowPrimaryContactOnDocuments(boolean isChecked) {
        clickCustomerDetailsTab();
        WebUI.setCheckbox(checkboxShowPrimaryContact, labelShowPrimaryContact, isChecked);

        return this;
    }

    public CustomersPage fillCustomerDetails(String company, String vatNumber, String phone, String website) {
        clickCustomerDetailsTab();
        WebUI.setText(inputCompany, company);
        WebUI.setText(inputVatNumber, vatNumber);
        WebUI.setText(inputPhone, phone);
        WebUI.setText(inputWebsite, website);

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
        WebUI.setText(textareaAddress, address);
        WebUI.setText(inputCity, city);
        WebUI.setText(inputState, state);
        WebUI.setText(inputZipCode, zipCode);
        selectPickerByText(selectCountry, "country", countryName);

        return this;
    }

    public CustomersPage fillBillingAddress(String street, String city, String state, String zipCode, String countryName) {
        clickBillingAndShippingTab();
        WebUI.setText(textareaBillingStreet, street);
        WebUI.setText(inputBillingCity, city);
        WebUI.setText(inputBillingState, state);
        WebUI.setText(inputBillingZipCode, zipCode);
        selectPickerByText(selectBillingCountry, "billing_country", countryName);

        return this;
    }

    public CustomersPage fillShippingAddress(String street, String city, String state, String zipCode, String countryName) {
        clickBillingAndShippingTab();
        WebUI.setText(textareaShippingStreet, street);
        WebUI.setText(inputShippingCity, city);
        WebUI.setText(inputShippingState, state);
        WebUI.setText(inputShippingZipCode, zipCode);
        selectPickerByText(selectShippingCountry, "shipping_country", countryName);

        return this;
    }

    public CustomersPage clickBillingSameAsCustomerInfo() {
        clickBillingAndShippingTab();
        WebUI.clickElement(linkBillingSameAsCustomerInfo);

        return this;
    }

    public CustomersPage clickCopyBillingAddress() {
        clickBillingAndShippingTab();
        WebUI.clickElement(linkCopyBillingAddress);

        return this;
    }

    public CustomersPage clickSaveButton() {
        WebUI.clickElement(buttonSave, 10);

        return this;
    }

    public CustomersPage clickSaveAndCreateContactButton() {
        WebUI.clickElement(buttonSaveAndCreateContact, 10);

        return this;
    }

    public CustomersPage waitForCustomerProfilePage() {
        WebUI.waitForPageLoaded();
        wait.until(ExpectedConditions.urlMatches(".*/admin/clients/client/\\d+$"));
        WebUI.waitForElementVisible(inputCompany);

        return this;
    }

    public boolean isCustomersTableDisplayed() {
        WebUI.waitForPageLoaded();
        return WebUI.isElementVisible(tableCustomers);
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
        WebUI.hover(companyNameLink);

        By deleteCustomerLink = getDeleteCustomerLink(companyName);
        WebUI.clickElement(deleteCustomerLink);

        WebUI.acceptAlert();

        WebUI.waitForPageLoaded();
        WebUI.waitForElementVisible(headerCustomersSummary);
        searchCustomer(companyName);
        wait.until(driver -> !this.driver.findElement(tableCustomersBody).getText().contains(companyName));

        return this;
    }

    public CustomersPage deleteCustomerByHoverAndConfirmAlert(String companyName) {
        searchCustomer(companyName);

        By companyNameLink = getCompanyNameLink(companyName);
        WebUI.hover(companyNameLink);

        By deleteCustomerLink = getDeleteCustomerLink(companyName);
        WebUI.clickElement(deleteCustomerLink);

        WebUI.acceptAlert();

        wait.until(driver -> !this.driver.findElement(tableCustomersBody).getText().contains(companyName));

        return this;
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCompanyValue() {
        return WebUI.getInputValue(inputCompany);
    }

    public String getVatNumberValue() {
        return WebUI.getInputValue(inputVatNumber);
    }

    public String getPhoneValue() {
        return WebUI.getInputValue(inputPhone);
    }

    public String getWebsiteValue() {
        return WebUI.getInputValue(inputWebsite);
    }

    public String getAddressValue() {
        return WebUI.getInputValue(textareaAddress);
    }

    public String getCityValue() {
        return WebUI.getInputValue(inputCity);
    }

    public String getStateValue() {
        return WebUI.getInputValue(inputState);
    }

    public String getZipCodeValue() {
        return WebUI.getInputValue(inputZipCode);
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


    private String getSelectPickerTitle(By locator) {
        return WebUI.getElementAttribute(locator, "title");
    }


    private By getDeleteCustomerLink(String companyName) {
        return By.xpath("//table[@id='clients']//tbody/tr[contains(., " + xpathLiteral(companyName) + ")]//a[contains(@href,'/admin/clients/delete/') and contains(@class,'_delete')]");
    }

    private By getCompanyNameLink(String companyName) {
        return By.xpath("//table[@id='clients']//tbody/tr[contains(., " + xpathLiteral(companyName) + ")]//td[contains(@class,'sorting_1')]/a[normalize-space()=" + xpathLiteral(companyName) + "]");
    }

    private void selectPickerByText(By selectLocator, String selectId, String visibleText) {
        WebUI.waitForElementPresent(selectLocator);
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
