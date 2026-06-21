package com.linh.Bai16_ThucHanhCRMPage;

import com.linh.common.BaseTest;
import com.linh.keywords.WebUI;
import com.linh.locatorsCRM.LocatorsCRMPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class ThucHanhCRM extends BaseTest {

    @Test
    public void testLoginCRM_Success(){
        driver.get("https://crm.anhtester.com/admin");
//        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("admin@example.com");
//        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("123456");
//        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
//
//        WebUI.setText(driver, By.xpath("//input[@type='email']"),"admin@example.com",10);
//        WebUI.setText(driver, By.xpath("//input[@type='password']"),"123456",10);
//        WebUI.clickElement(driver, By.xpath("//button[normalize-space()='Login']"),10);

        WebUI.setText(driver, LocatorsCRMPage.inputEmail,"admin@example.com",10);
        WebUI.setText(driver, LocatorsCRMPage.inputPassword,"123456");
        WebUI.clickElement(driver, LocatorsCRMPage.buttonLogin);


        boolean checkDashboard = WebUI.isElementPresent(driver, LocatorsCRMPage.menuDashboard);
        Assert.assertTrue(checkDashboard, "LOGIN FAIL, DASHBOARD PAGE DOES NOT DISPLAY YET");
    }

    @Test
    public void testLoginFailWithEmailInvalid(){
        driver.get("https://crm.anhtester.com/admin");
        WebUI.setText(driver, LocatorsCRMPage.inputEmail,"admin123@example.com",10);
        WebUI.setText(driver, LocatorsCRMPage.inputPassword,"123456");
        WebUI.clickElement(driver, LocatorsCRMPage.buttonLogin);

        boolean checkAlertErrorMessage = WebUI.isElementPresent(driver, LocatorsCRMPage.alertErrorMessage);
        Assert.assertTrue(checkAlertErrorMessage, "Login failed! Alert Error message is not displayed.");
        Assert.assertEquals(driver.findElement(LocatorsCRMPage.alertErrorMessage).getText(), "Invalid email or password");

    }
    @Test
    public void testLoginFailWithPasswordInvalid(){
        driver.get("https://crm.anhtester.com/admin");
        WebUI.setText(driver, LocatorsCRMPage.inputEmail,"admin@example.com",10);
        WebUI.setText(driver, LocatorsCRMPage.inputPassword,"123");
        WebUI.clickElement(driver, LocatorsCRMPage.buttonLogin);

        boolean checkAlertErrorMessage = WebUI.isElementPresent(driver, LocatorsCRMPage.alertErrorMessage);
        Assert.assertTrue(checkAlertErrorMessage, "Login failed! Alert Error message is not displayed.");
        Assert.assertEquals(driver.findElement(LocatorsCRMPage.alertErrorMessage).getText(), "Invalid email or password");

    }

    @Test
    public void testLoginFailWithEmailNull(){
        driver.get("https://crm.anhtester.com/admin");
        WebUI.setText(driver, LocatorsCRMPage.inputEmail,"",10);
        WebUI.setText(driver, LocatorsCRMPage.inputPassword,"123");
        WebUI.clickElement(driver, LocatorsCRMPage.buttonLogin);

        boolean checkAlertErrorMessage = WebUI.isElementPresent(driver, LocatorsCRMPage.alertEmailRequiredMessage);
        Assert.assertTrue(checkAlertErrorMessage, "Login failed! Alert Error message is not displayed.");
        Assert.assertEquals(driver.findElement(LocatorsCRMPage.alertEmailRequiredMessage).getText(), "The Email Address field is required.");

    }
    @Test
    public void testLoginFailWithPasswordNull(){
        driver.get("https://crm.anhtester.com/admin");
        WebUI.setText(driver, LocatorsCRMPage.inputEmail,"admin@example.com",10);
        WebUI.setText(driver, LocatorsCRMPage.inputPassword,"");
        WebUI.clickElement(driver, LocatorsCRMPage.buttonLogin);

        boolean checkAlertErrorMessage = WebUI.isElementPresent(driver, LocatorsCRMPage.alertPasswordRequiredMessage);
        Assert.assertTrue(checkAlertErrorMessage, "Login failed! Alert Error message is not displayed.");
        Assert.assertEquals(driver.findElement(LocatorsCRMPage.alertPasswordRequiredMessage).getText(), "The Password field is required.");

    }

    @Test
    public void testLoginFailWithEmailAndPasswordNull(){
        driver.get("https://crm.anhtester.com/admin");
        WebUI.setText(driver, LocatorsCRMPage.inputEmail,"",10);
        WebUI.setText(driver, LocatorsCRMPage.inputPassword,"");
        WebUI.clickElement(driver, LocatorsCRMPage.buttonLogin);

        boolean checkAlertRequiredEmail = WebUI.isElementPresent(driver, LocatorsCRMPage.alertEmailRequiredMessage);
        boolean checkAlertRequiredPassword = WebUI.isElementPresent(driver, LocatorsCRMPage.alertPasswordRequiredMessage);
        Assert.assertTrue(checkAlertRequiredEmail, "Login failed! The Email Error Message is not present.");
        Assert.assertTrue(checkAlertRequiredPassword, "Login failed! The Password Error Message is not present.");
        Assert.assertEquals(driver.getCurrentUrl(),"https://crm.anhtester.com/admin/authentication","The current URL is incorrect ");

    }

    @Test
    public void testLoginFailWithEmailFormatInvalid_01 (){
        driver.get("https://crm.anhtester.com/admin");
        WebUI.setText(driver, LocatorsCRMPage.inputEmail,"admin@",10);
        WebUI.setText(driver, LocatorsCRMPage.inputPassword,"123456");
        WebUI.clickElement(driver, LocatorsCRMPage.buttonLogin);

        //Handle HTML5 validation message
        //https://anhtester.com/blog/how-to-get-html5-validation-message-with-selenium-b654.html
        String emailValidationMessage = driver.findElement(LocatorsCRMPage.inputEmail).getAttribute("validationMessage");
        System.out.println(emailValidationMessage);
        Assert.assertEquals(emailValidationMessage,"Please enter a part following '@'. 'admin@' is incomplete.", "Fail. The HTML5 Error Message is not match.");
    }
    @Test
    public void testLoginFailWithEmailFormatInvalid_02() {
        driver.get("https://crm.anhtester.com/admin");
        WebUI.setText(driver, LocatorsCRMPage.inputEmail,"admin@example",10);
        WebUI.setText(driver, LocatorsCRMPage.inputPassword,"123456");
        WebUI.clickElement(driver, LocatorsCRMPage.buttonLogin);

        boolean checkAlertErrorMessage = WebUI.isElementPresent(driver, LocatorsCRMPage.alertErrorMessage);
        Assert.assertTrue(checkAlertErrorMessage, "Login failed! Alert Error message is not displayed.");
        Assert.assertEquals(driver.findElement(LocatorsCRMPage.alertErrorMessage).getText(), "The Email Address field must contain a valid email address.");

    }


}
