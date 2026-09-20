package com.linh.Bai26_Parameters_MultiBrowser.pages;

import com.linh.constants.ConfigData;
import com.linh.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class LoginPage extends BasePage {
    //Khai báo driver cục bộ trong chính class này
    private WebDriver driver;
    private WebDriverWait wait;
    public String LOGIN_URL= ConfigData.LoginURL;
    public  String LOGIN_PAGE_TITLE= "Perfex CRM | Anh Tester Demo - Login";
    public  String LOGIN_PAGE_HEADER_TEXT= "Login";


    //Khai báo các element dạng đối tượng By (phương thức tìm kiếm)
    private By headerPage = By.xpath("//h1[normalize-space()='Login']");
    private By inputEmail = By.xpath("//input[@id='email']");
    private By inputPassword = By.xpath("//input[@id='password']");
    private By buttonLogin = By.xpath("//button[normalize-space()='Login']");
    private By alertEmailRequiredMessage = By.xpath("//div[normalize-space() ='The Email Address field is required.']");
    private By alertPasswordRequiredMessage = By.xpath("//div[normalize-space() ='The Password field is required.']");
   // private By errorMessage = By.xpath("//div[@id='alerts']");
    private By errorMessage = By.xpath("//div[contains (@class,'alert-danger')]");


    //Khai báo hàm xây dựng, để truyền driver từ bên ngoài vào chính class này sử dụng
    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver; // truyen gia tri cho driver
        new WebUI(driver);
        //driver = _driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // khoi tao gia tri cho wait
    }

    public void verifyNavigateToLoginPage(){
        //Title, URL, Header
        WebUI.waitForPageLoaded();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(driver.getTitle(), LOGIN_PAGE_TITLE, "Fail, The Login  page title not match.");
        Assert.assertEquals(driver.getCurrentUrl(), LOGIN_URL, "Fail, The Login Url is not match");
        softAssert .assertEquals(driver.findElement(headerPage).getText(), LOGIN_PAGE_HEADER_TEXT,"Fail, The header page is not match");
        softAssert.assertAll();
    }

    public String getHeaderLoginPage (){
        WebUI.waitForPageLoaded();
        return WebUI.getElementText(headerPage);
    }

    private void setEmail(String email) {
        WebUI.setText(inputEmail,email);
    }

    private void setPassword(String password) {
        WebUI.setSecretText(inputPassword,password);
    }

    private void clickLoginButton() {
        WebUI.clickElement(buttonLogin);
    }

    public void verifyLoginSuccess() {
        new DashBoardPage(driver).verifyNavigateToDashBoardPage();
        WebUI.waitForCurrentURLContains("/admin/");
        Assert.assertTrue((driver.getCurrentUrl().contains("/admin/")),"FAIL. Không chuyển hướng đến trang DashBoard" );

       // wait.until(ExpectedConditions.urlContains("crm.anhtester.com/admin/"));
        Assert.assertFalse(driver.getCurrentUrl().contains("authentication"), "FAIL. Vẫn đang ở trang Login");
    }

    public void verifyLoginFail(String message) {
        WebUI.waitForPageLoaded();
        WebUI.waitForElementVisible(errorMessage);
        Assert.assertTrue(WebUI.checkElementExist(errorMessage,10,1000), "Error message NOT displays");
        WebUI.assertEquals(WebUI.getElementText(errorMessage), message, "Content of error massage NOT match.");
        WebUI.assertContains(driver.getCurrentUrl(),"authentication","FAIL. Không còn ở trang Login");
    }
    public void verifyLoginFailWithEmailAndPasswordNull() {

        boolean checkAlertRequiredEmail = WebUI.checkElementExist(alertEmailRequiredMessage);
        boolean checkAlertRequiredPassword = WebUI.checkElementExist(alertPasswordRequiredMessage);
        Assert.assertTrue(checkAlertRequiredEmail, "Login failed! The Email Error Message is not present.");
        Assert.assertTrue(checkAlertRequiredPassword, "Login failed! The Password Error Message is not present.");
        Assert.assertEquals(WebUI.getCurrentURL(),"https://crm.anhtester.com/admin/authentication","The current URL is incorrect ");
    }

    public void verifyAlertFormatEmailInvalid() {

        //Handle HTML5 validation message
        //https://anhtester.com/blog/how-to-get-html5-validation-message-with-selenium-b654.html

        Assert.assertEquals(WebUI.getElementProperty(inputEmail,"validationMessage"),"Please enter a part following '@'. 'admin@' is incomplete.", "Fail. The HTML5 Error Message is not match.");

    }


    //Các hàm xử lý cho chính trang này
    public DashBoardPage loginCRM(String email, String password) {
        WebUI.openURL(ConfigData.LoginURL);// Goi class Config
        verifyNavigateToLoginPage();
        setEmail(email);
        setPassword(password);
        clickLoginButton();
        return  new DashBoardPage(driver);

    }
    public DashBoardPage loginCRM() {

        WebUI.openURL(ConfigData.LoginURL);// Goi class Config
        verifyNavigateToLoginPage();
        setEmail(ConfigData.EMAIL_ADMIN);
        setPassword(ConfigData.PASSWORD_ADMIN);
        clickLoginButton();
        verifyLoginSuccess();
        return new DashBoardPage(driver);
    }


}
