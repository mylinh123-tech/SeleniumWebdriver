package com.linh.Bai22_23_ThucHanhPOM.pages;

import com.linh.constants.ConfigData;
import com.linh.keywords.ActionKeyword;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
        //driver = _driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // khoi tao gia tri cho wait
    }

    public void verifyNavigateToLoginPage(){
        //Title, URL, Header
        ActionKeyword.waitForPageLoaded(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(driver.getTitle(), LOGIN_PAGE_TITLE, "Fail, The Login  page title not match.");
        Assert.assertEquals(driver.getCurrentUrl(), LOGIN_URL, "Fail, The Login Url is not match");
        softAssert .assertEquals(driver.findElement(headerPage).getText(), LOGIN_PAGE_HEADER_TEXT,"Fail, The header page is not match");
        softAssert.assertAll();
    }

    public String getHeaderLoginPage (){
        ActionKeyword.waitForPageLoaded(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(headerPage));
        return driver.findElement(headerPage).getText();
    }

    private void setEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputEmail));
        driver.findElement(inputEmail).sendKeys(email);
    }

    private void setPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputPassword));
        driver.findElement(inputPassword).sendKeys(password);
    }

    private void clickLoginButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(buttonLogin));
        driver.findElement(buttonLogin).click();
    }

    public void verifyLoginSuccess() {
        ActionKeyword.waitForPageLoaded(driver);
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("authentication")));
        wait.until(ExpectedConditions.urlToBe(ConfigData.Base_URL + "/admin/"));
        Assert.assertTrue((driver.getCurrentUrl().contains("/admin/")),"FAIL. Không chuyển hướng đến trang DashBoard" );

       // wait.until(ExpectedConditions.urlContains("crm.anhtester.com/admin/"));
        Assert.assertFalse(driver.getCurrentUrl().contains("authentication"), "FAIL. Vẫn đang ở trang Login");
    }

    public void verifyLoginFail(String message) {
        ActionKeyword.waitForPageLoaded(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        Assert.assertTrue(driver.findElement(errorMessage).isDisplayed(), "Error message NOT displays");
        Assert.assertEquals(driver.findElement(errorMessage).getText(), message, "Content of error massage NOT match.");
        Assert.assertTrue(driver.getCurrentUrl().contains("authentication"), "FAIL. Không còn ở trang Login");
    }
    public void verifyLoginFailWithEmailAndPasswordNull() {

        boolean checkAlertRequiredEmail = ActionKeyword.isElementPresent(driver, alertEmailRequiredMessage);
        boolean checkAlertRequiredPassword = ActionKeyword.isElementPresent(driver, alertPasswordRequiredMessage);
        Assert.assertTrue(checkAlertRequiredEmail, "Login failed! The Email Error Message is not present.");
        Assert.assertTrue(checkAlertRequiredPassword, "Login failed! The Password Error Message is not present.");
        Assert.assertEquals(driver.getCurrentUrl(),"https://crm.anhtester.com/admin/authentication","The current URL is incorrect ");
    }

    public void verifyAlertFormatEmailInvalid() {

        //Handle HTML5 validation message
        //https://anhtester.com/blog/how-to-get-html5-validation-message-with-selenium-b654.html
        String emailValidationMessage = driver.findElement(inputEmail).getAttribute("validationMessage");
        System.out.println(emailValidationMessage);
        Assert.assertEquals(emailValidationMessage,"Please enter a part following '@'. 'admin@' is incomplete.", "Fail. The HTML5 Error Message is not match.");

    }


    //Các hàm xử lý cho chính trang này
    public DashBoardPage loginCRM(String email, String password) {

        driver.get(ConfigData.LoginURL);// Goi class Config
        verifyNavigateToLoginPage();
        setEmail(email);
        setPassword(password);
        clickLoginButton();
        return  new DashBoardPage(driver);

    }
    public DashBoardPage loginCRM() {

        driver.get(ConfigData.LoginURL);// Goi class Config
        verifyNavigateToLoginPage();
        setEmail(ConfigData.EMAIL_ADMIN);
        setPassword(ConfigData.PASSWORD_ADMIN);
        clickLoginButton();
        verifyLoginSuccess();
        return new DashBoardPage(driver);
    }


}
