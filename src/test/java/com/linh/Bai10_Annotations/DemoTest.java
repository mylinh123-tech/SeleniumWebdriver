package com.linh.Bai10_Annotations;

import org.testng.annotations.Test;

public class DemoTest {
    @Test(priority = 1, description = "Test login CRM")
    public void TestLoginCRM() throws Exception {
        System.out.println("Testcase login CRM");
        throw new Exception("Testcase fail");
    }

    //Enable = false -> skip this testcase
    @Test (priority = 2, description = "Test logout CRM", enabled = false)
    public void TestLogoutCRM(){
        System.out.println("Testcase logout CRM");
    }

    //timeout -> run testcase within the setup time
    @Test(priority = 3,description = "Test login CRM fail", timeOut = 5000)
    public void TestLoginCRMFail() throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("Testcase login CRM fail with invalid email");
    }

    //alwaysRun is allowed to run even thought dependsOnMethods
    @Test (dependsOnMethods = {"TestLoginCRM"},alwaysRun = true, description = "Test Login success then run this testcase")
    public void AddNewCCustomer(){
        System.out.println("Testcase add new customer");
    }

}
