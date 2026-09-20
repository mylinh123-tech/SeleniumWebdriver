package com.linh.Bai24_25_VietHamChung_WebUI.testcases;

import com.linh.Bai24_25_VietHamChung_WebUI.pages.DashBoardPage;
import com.linh.Bai24_25_VietHamChung_WebUI.pages.LoginPage;
import com.linh.common.BaseTest;
import com.linh.constants.ConfigData;
import com.linh.keywords.WebUI;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private DashBoardPage dashBoardPage;
    private SoftAssert softAssert;
    @BeforeClass
    public void beforeClass(){
        softAssert =new SoftAssert();
    }
    @AfterClass
    public void afterClass(){
        softAssert.assertAll();
    }


    @BeforeMethod
    public void beforeMethod(){
        loginPage  = new LoginPage(driver);
    }


    @Test
    public void testPatternAAA()  {
       WebUI.openURL(ConfigData.LoginURL);
       String email = "admin@example.com";
       String password = "123456";
       softAssert.assertEquals(WebUI.getCurrentURL(), ConfigData.LoginURL,"Fail, the URL incorrect");
       softAssert.assertEquals(loginPage.getHeaderLoginPage(), loginPage.LOGIN_PAGE_HEADER_TEXT,"Fail, the login page header is not match");
       dashBoardPage = loginPage.loginCRM(email, password);
       Assert.assertTrue(dashBoardPage.isDashBoardPageOpen(),"Fail, can not redirect to dashboard page");

    }


    @Test (priority = 1)
    public void testLoginCRM_Success()  {

        dashBoardPage = loginPage.loginCRM("admin@example.com","123456");
        loginPage.verifyLoginSuccess();
        Assert.assertTrue(dashBoardPage.isDashBoardPageOpen(),"Fail, can not redirect to dashboard page");


    }

    @Test (priority = 2)
    public void testLoginFailWithEmailInvalid(){

        dashBoardPage = loginPage.loginCRM("admin123@example.com","123456");
        loginPage.verifyLoginFail("Invalid email or password");
    }

    @Test (priority = 3)
    public void testLoginFailWithPasswordInvalid(){
        loginPage.loginCRM("admin@example.com","123");
        loginPage.verifyLoginFail("Invalid email or password");
    }

    @Test (priority = 4)
    public void testLoginFailWithEmailNull(){
        loginPage.loginCRM("","123456");
        loginPage.verifyLoginFail("The Email Address field is required.");

    }

    @Test (priority = 5)
    public void testLoginFailWithPasswordNull(){
        loginPage.loginCRM("admin@example.com","");
        loginPage.verifyLoginFail("The Password field is required.");

    }

    @Test(priority = 6)
    public void testLoginFailWithEmailAndPasswordNull(){
        loginPage.loginCRM("","");
        loginPage.verifyLoginFailWithEmailAndPasswordNull();
    }

    @Test (priority = 7)
    public void testLoginFailWithEmailFormatInvalid_01 (){
        loginPage.loginCRM("admin@","123456");
        loginPage.verifyAlertFormatEmailInvalid();
    }


    @Test(priority = 8)
    public void testLoginFailWithEmailFormatInvalid_02() {
        loginPage.loginCRM("admin@example","123456");
        loginPage.verifyLoginFail("The Email Address field must contain a valid email address.");
    }
}
