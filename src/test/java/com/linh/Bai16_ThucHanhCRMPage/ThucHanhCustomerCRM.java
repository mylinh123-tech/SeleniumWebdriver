package com.linh.Bai16_ThucHanhCRMPage;

import com.linh.common.BaseTest;
import com.linh.keywords.WebUI;
import com.linh.locatorsCRM.LocatorsCRMPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class ThucHanhCustomerCRM extends BaseTest {
    @BeforeMethod
    public void loginCRM() {
        driver.get("https://crm.anhtester.com/admin/authentication");
        WebUI.setText(driver, LocatorsCRMPage.inputEmail, "admin@example.com", 10);
        WebUI.setText(driver, LocatorsCRMPage.inputPassword, "123456");
        WebUI.clickElement(driver, By.xpath("//button[normalize-space()='Login']"));

        boolean checkDashboardMenu = WebUI.isElementPresent(driver, LocatorsCRMPage.menuDashboard, 5);
        Assert.assertTrue(checkDashboardMenu, "Login Fail. Dashboard Menu is not present");
    }

    @Test(priority = 1)
    public void testAddNewCustomer() {
        WebUI.clickElement(driver, By.xpath("//span[normalize-space()='Customers']"), 10);
        boolean checkCustomerPage = WebUI.isElementPresent(driver, By.xpath("//span[normalize-space()='Customers Summary']"), 10);
        Assert.assertTrue(checkCustomerPage, "Customer Page is not present.");
        WebUI.clickElement(driver, By.xpath("//a[normalize-space()='New Customer']"), 10);

        // Cho form load xong bang ham chung
        if (!WebUI.isElementPresent(driver, By.id("company"), 10)) {
            throw new RuntimeException("Form Add Customer khong load duoc.");
        }

        // Du lieu dung chung cho buoc dien form va buoc verify
        String companyName = "Selenium Auto Test " + System.currentTimeMillis();
        String vat = "0312345678";
        String phone = "0901234567";
        String website = "https://anhtester.com";
        String address = "123 Test Street";
        String city = "Tra Vinh";
        String state = "Vinh Long";
        String zip = "94000";
        String currency = "USD";
        String language = "Vietnamese";
        String country = "Vietnam";
        String group = "Gold";
        System.out.println(companyName);

        // --- Tab "Customer Details" ---
        WebUI.setText(driver, By.xpath("//input[@id='company']"), companyName, 10); // * bat buoc
        WebUI.setText(driver, By.xpath("//input[@id='vat']"), vat);
        WebUI.setText(driver, By.xpath("//input[@id='phonenumber']"), phone);
        WebUI.setText(driver, By.xpath("//input[@id='website']"), website);
        WebUI.setText(driver, By.xpath("//textarea[@id='address']"), address);
        WebUI.setText(driver, By.xpath("//input[@id='city']"), city);
        WebUI.setText(driver, By.xpath("//input[@id='state']"), state);
        WebUI.setText(driver, By.xpath("//input[@id='zip']"), zip);

        // Dropdown selectpicker -> dung helper cuc bo (WebUI chua ho tro)
        selectPickerByText("default_currency", currency);
        selectPickerByText("default_language", language);
        selectPickerByText("country", country);

        // Field "Group" la MULTI-select, id co dau ngoac: groups_in[]
        //selectPickerByText("groups_in[]", group);

        WebUI.clickElement(driver, By.xpath("//button[@data-id='groups_in[]']"));
        sleep(1);
        WebUI.setText(driver, By.xpath("//button[@data-id='groups_in[]']/following-sibling::div//input"), group);
        sleep(1);
        WebUI.clickElement(driver, By.xpath("//span[normalize-space()='" + group + "']"));
        sleep(1);
        WebUI.clickElement(driver, By.xpath("//button[@data-id='groups_in[]']"));

        // --- Save --- (co 2 nut "Save"; nut submit cua form co class 'only-save')
        WebUI.clickElement(driver, By.xpath("//button[normalize-space()='Save and create contact']/following-sibling::button[normalize-space()='Save']"));

        // ====== VERIFY KET QUA SAU KHI SAVE ======
        // Sau khi Save thanh cong, Perfex redirect sang trang profile (load lai form
        // voi day du gia tri da luu) -> verify lai TAT CA cac field tren trang profile.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1) Luu thanh cong -> redirect sang /admin/clients/client/<id>
        wait.until(ExpectedConditions.urlMatches(".*/admin/clients/client/\\d+$"));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.matches(".*/admin/clients/client/\\d+$"),
                "Save that bai - URL khong chuyen sang trang profile khach hang: " + currentUrl);
        System.out.println("Customer da luu, URL profile: " + currentUrl);

        // 2) Tieu de trang profile = dung ten cong ty vua tao
        Assert.assertEquals(driver.getTitle(), companyName,
                "Ten cong ty tren tieu de trang profile khong khop");

        // 3) Verify tung field text (input/textarea) qua thuoc tinh 'value'
        verifyInputValue(By.id("company"), companyName, "Company");
        verifyInputValue(By.id("vat"), vat, "VAT");
        verifyInputValue(By.id("phonenumber"), phone, "Phone");
        verifyInputValue(By.id("website"), website, "Website");
        verifyInputValue(By.id("address"), address, "Address");
        verifyInputValue(By.id("city"), city, "City");
        verifyInputValue(By.id("state"), state, "State");
        verifyInputValue(By.id("zip"), zip, "Zip");

        // 4) Verify tung dropdown selectpicker qua thuoc tinh 'title' cua nut hien thi
        verifyPickerValue("default_currency", currency, "Currency");
        verifyPickerValue("default_language", language, "Language");
        verifyPickerValue("country", country, "Country");
        verifyPickerValue("groups_in[]", group, "Group");

        System.out.println("Verify thanh cong tat ca cac field.");

    }

    /**
     * Verify gia tri cua mot field text (input/textarea) qua thuoc tinh 'value'.
     */
    private void verifyInputValue(By locator, String expected, String fieldName) {
        Assert.assertTrue(WebUI.isElementPresent(driver, locator, 10),
                "Khong tim thay field '" + fieldName + "' tren trang profile");
        String actual = driver.findElement(locator).getAttribute("value");
        Assert.assertEquals(actual, expected,
                "Field '" + fieldName + "' luu sai. Mong doi: '" + expected + "' | Thuc te: '" + actual + "'");
    }

    /**
     * Verify gia tri da chon cua dropdown selectpicker qua thuoc tinh 'title' cua nut hien thi.
     * (The <select> goc bi bootstrap-select an di nen doc tren nut button[data-id] la chac chan nhat.)
     */
    private void verifyPickerValue(String selectId, String expected, String fieldName) {
        By button = By.xpath("//button[@data-id='" + selectId + "']");
        Assert.assertTrue(WebUI.isElementPresent(driver, button, 10),
                "Khong tim thay field '" + fieldName + "' tren trang profile");
        String actual = driver.findElement(button).getAttribute("title");
        Assert.assertEquals(actual, expected,
                "Field '" + fieldName + "' luu sai. Mong doi: '" + expected + "' | Thuc te: '" + actual + "'");
    }

    /**
     * Chon gia tri cho dropdown dang bootstrap-select (selectpicker) bang JavascriptExecutor.
     * Ho tro ca single-select (currency, language, country) lan multi-select (Group: groups_in[]).
     */
    private void selectPickerByText(String selectId, String visibleText) {
        String js =
                "var sel=document.getElementById(arguments[0]);" +
                        "if(!sel){return 'NO_SELECT';}" +
                        "var found=false;" +
                        "if(sel.multiple){" +                      // multi-select -> phai dung option.selected
                        "  for(var i=0;i<sel.options.length;i++){" +
                        "    if(sel.options[i].text.trim()===arguments[1]){sel.options[i].selected=true;found=true;break;}" +
                        "  }" +
                        "}else{" +                                 // single-select -> set value
                        "  for(var j=0;j<sel.options.length;j++){" +
                        "    if(sel.options[j].text.trim()===arguments[1]){sel.value=sel.options[j].value;found=true;break;}" +
                        "  }" +
                        "}" +
                        // bootstrap-select can refresh thi nut hien thi moi cap nhat
                        "if(window.jQuery){jQuery(sel).selectpicker('refresh');jQuery(sel).trigger('change');}" +
                        "else{sel.dispatchEvent(new Event('change'));}" +
                        "return found?'OK':'NO_OPTION';";
        Object result = ((JavascriptExecutor) driver).executeScript(js, selectId, visibleText);
        if (!"OK".equals(result)) {
            throw new RuntimeException("selectPickerByText that bai (" + result + "): select #" + selectId
                    + ", option = '" + visibleText + "'");
        }
    }
}
