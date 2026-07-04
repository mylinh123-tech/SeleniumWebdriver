package com.linh.Bai17PageObjectModel.testcases;
import com.linh.Bai17PageObjectModel.pages.LoginPage;
import com.linh.common.BaseTest;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;


    @Test (priority = 1)
    public void testLoginCRM_Success()  {
        loginPage = new LoginPage(driver);
        loginPage.loginCRM("admin@example.com","123456");
        loginPage.verifyLoginSuccess();

    }

    @Test (priority = 2)
    public void testLoginFailWithEmailInvalid(){

        loginPage = new LoginPage(driver);
        loginPage.loginCRM("admin123@example.com","123456");
        loginPage.verifyLoginFail("Invalid email or password");

    }

    @Test (priority = 3)
    public void testLoginFailWithPasswordInvalid(){
        loginPage = new LoginPage(driver);
        loginPage.loginCRM("admin@example.com","123");
        loginPage.verifyLoginFail("Invalid email or password");
    }

    @Test (priority = 4)
    public void testLoginFailWithEmailNull(){
        loginPage = new LoginPage(driver);
        loginPage.loginCRM("","123456");
        loginPage.verifyLoginFail("The Email Address field is required.");

    }

    @Test (priority = 5)
    public void testLoginFailWithPasswordNull(){
        loginPage = new LoginPage(driver);
        loginPage.loginCRM("admin@example.com","");
        loginPage.verifyLoginFail("The Password field is required.");

    }

    @Test(priority = 6)
    public void testLoginFailWithEmailAndPasswordNull(){
        loginPage = new LoginPage(driver);
        loginPage.loginCRM("","");
        loginPage.verifyLoginFailWithEmailAndPasswordNull();
    }

    @Test (priority = 7)
    public void testLoginFailWithEmailFormatInvalid_01 (){
        loginPage = new LoginPage(driver);
        loginPage.loginCRM("admin@","123456");
        loginPage.verifyAlertFormatEmailInvalid();
    }


    @Test(priority = 8)
    public void testLoginFailWithEmailFormatInvalid_02() {
        loginPage = new LoginPage(driver);
        loginPage.loginCRM("admin@example","123456");
        loginPage.verifyLoginFail("The Email Address field must contain a valid email address.");
    }
}
