package com.linh.Bai9TestNG;

import org.testng.annotations.Test;

public class DemoTestNG {
    @Test
    public void testLoginCRM(){
        System.out.println("This is testcase Login CRM");
    }
    @Test
    public void testLoginCRMFailWithEmailInvalid(){
        System.out.println("This is testcase Login CRM fail with invalid email");
    }
    @Test
    public void testLoginCRMFailWithPassWordInvalid(){
        System.out.println("This is testcase Login CRM fail with invalid password");
    }
}
